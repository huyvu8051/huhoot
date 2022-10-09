package com.huhoot.organize;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.huhoot.config.security.JwtUtil;
import com.huhoot.converter.ListConverter;
import com.huhoot.dto.ChallengeResponse;
import com.huhoot.encrypt.EncryptUtils;
import com.huhoot.enums.ChallengeStatus;
import com.huhoot.exception.ChallengeException;
import com.huhoot.host.manage.challenge.ChallengeMapper;
import com.huhoot.model.*;
import com.huhoot.repository.*;
import com.huhoot.vue.vdatatable.paging.PageResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class OrganizeServiceImpl implements OrganizeService {

    private final QuestionRepository questRepo;
    private final ParticipantRepository participantRepository;
    private final ChallengeRepository challengeRepository;
    private final SocketIOServer socketIOServer;
    private final ListConverter listConverter;

    private final CustomerRepository studentRepository;
    private final ParticipantRepository studentChallengeRepository;
    private final JwtUtil jwtUtil;
    private final EncryptUtils encryptUtils;
    private final ChallengeMapper challengeMapper;


    @Override
    public List<StudentInChallengeResponse> getAllStudentInChallengeIsLogin(Customer userDetails, int challengeId) {
        return participantRepository.findAllStudentIsLogin(challengeId, userDetails.getId());
    }


    /**
     * Start challenge, update challenge status to IN_PROGRESS
     *
     * @param challengeId challenge id
     * @param adminId     admin id
     */
    @Override
    public void startChallenge(int challengeId, int adminId) {

        challengeRepository.updateChallengeStatusById(ChallengeStatus.IN_PROGRESS, challengeId);

        socketIOServer.getRoomOperations(challengeId + "").sendEvent("startChallenge");

    }


    private final AnswerRepository answerRepository;

    private final StudentAnswerRepository studentAnswerRepository;

    /**
     * Sent all AnswerResponse to all {@link SocketIOClient} in {@link com.corundumstudio.socketio.BroadcastOperations}
     *
     * @throws NullPointerException not found exception
     */


    @Override
    public PageResponse showCorrectAnswer(int challengeId) throws NullPointerException {
        PublishedExam currentPublishedExam = getCurrentPublishedExam(challengeId);


        Question question = questRepo.findOneById(currentPublishedExam.getQuestion().getId()).orElseThrow(() -> new NullPointerException("No question"));

        List<Integer> answerResult = answerRepository.findAllCorrectAnswerIds(currentPublishedExam.getQuestion().getId());

        socketIOServer.getRoomOperations(challengeId + "").sendEvent("showCorrectAnswer", CorrectAnswerResponse.builder().corrects(answerResult).timeout(question.getTimeout()).build());

        Page<StudentScoreResponse> response = studentAnswerRepository.findTopStudent(challengeId, Pageable.unpaged());


        return listConverter.toPageResponse(response);
    }

    /**
     * @param challengeId {@link Challenge} id
     * @param pageable    {@link Pageable}
     * @return List of top 20 customer have best total challenge score
     */
    @Override
    public PageResponse<StudentScoreResponse> getTopStudent(int challengeId, Pageable pageable) {

        Page<StudentScoreResponse> response = studentAnswerRepository.findTopStudent(challengeId, pageable);

        return listConverter.toPageResponse(response);
    }


    /**
     * Set challenge status ENDED and sent endChallenge event to all Client in Room
     *
     * @param challengeId {@link Challenge} id
     * @throws NullPointerException not found
     */
    @Override
    public void endChallenge(int challengeId) throws NullPointerException {

        Optional<Challenge> optional = challengeRepository.findOneById(challengeId);
        optional.orElseThrow(() -> new NullPointerException("Challenge not found"));

        challengeRepository.updateChallengeStatusById(ChallengeStatus.ENDED, challengeId);

        socketIOServer.getRoomOperations(challengeId + "").sendEvent("endChallenge");
    }


    /**
     * @param studentIds  List of {@link Customer} ids
     * @param challengeId {@link Challenge} id
     * @param adminId     {@link Customer} id
     */
    @Override
    public void kickStudent(List<Integer> studentIds, int challengeId, int adminId) {
        List<Participant> participants = participantRepository.findAllByStudentIdInAndChallengeIdAndAdminId(studentIds, challengeId, adminId);


        for (Participant sic : participants) {
            try {
                SocketIOClient client = socketIOServer.getClient(sic.getCustomer().getSocketId());
                client.sendEvent("kickStudent");
                client.disconnect();
            } catch (Exception err) {
                log.error(err.getMessage());
            }

        }


        participantRepository.saveAll(participants);

    }

    @Override
    public void publishNextQuestion(int challengeId) throws Exception {

        Question question = questRepo.findFirstByChallengeIdAndAskDateNullOrderByOrdinalNumberAsc(challengeId).orElseThrow(() -> new Exception("Not found or empty question"));


        int countQuestion = questRepo.countQuestionInChallenge(challengeId);
        int questionOrder = questRepo.findNumberOfPublishedQuestion(challengeId) + 1;

        PublishQuestion publishQuest = PublishQuestion.builder().id(question.getId()).ordinalNumber(question.getOrdinalNumber()).content(question.getContent()).image(question.getImage()).timeLimit(question.getTimeLimit().getValue()).challengeId(challengeId).totalQuestion(countQuestion).questionOrder(questionOrder).theLastQuestion(countQuestion == questionOrder).build();

        List<AnswerResultResponse> publishAnswers = answerRepository.findAllPublishAnswer(question.getId());


        // delay 6 sec
        long sec = 6;
        long askDate = System.currentTimeMillis() + sec * 1000;

        long timeout = askDate + question.getTimeLimit().getValue() * 1000;

        publishQuest.setAskDate(askDate);
        publishQuest.setTimeout(timeout);



        // hash correct answer ids

        List<PublishAnswer> publishAnswers2 = answerRepository.findAllAnswerByQuestionIdAndAdminId(question.getId());

        String questionToken = encryptUtils.generateQuestionToken(publishAnswers2, askDate, question.getTimeLimit().getValue());

        // update ask date and decryptKey
        questRepo.updateAskDateAndPublishedOrderNumberEncryptKey(askDate, timeout, questionOrder, question.getId(), questionToken.getBytes());

        socketIOServer.getRoomOperations(challengeId + "").sendEvent("publishQuestion", PublishedExam.builder().question(publishQuest).answers(publishAnswers).build());


    }

    public PublishedExam getCurrentPublishedExam(int challengeId) {
        Optional<Question> op = questRepo.findFirstByChallengeIdAndAskDateNotNullOrderByAskDateDesc(challengeId);

        Challenge challenge = challengeRepository.findOneById(challengeId).orElseThrow(() -> new NullPointerException("Challenge not found"));
        ChallengeResponse challengeResponse = challengeMapper.toDto(challenge);
        if (!op.isPresent()) {
            return PublishedExam.builder().challenge(challengeResponse).build();
        }

        Question currQuestion = op.get();

        int countQuestion = questRepo.countQuestionInChallenge(challengeId);
        int questionOrder = questRepo.findNumberOfPublishedQuestion(challengeId) + 1;

        PublishQuestion publishQuest = PublishQuestion.builder().id(currQuestion.getId()).ordinalNumber(currQuestion.getOrdinalNumber()).askDate(currQuestion.getAskDate()).content(currQuestion.getContent()).image(currQuestion.getImage()).timeLimit(currQuestion.getTimeLimit().getValue()).challengeId(challengeId).totalQuestion(countQuestion).questionOrder(questionOrder).theLastQuestion(countQuestion == questionOrder).build();

        List<AnswerResultResponse> publishAnswers = answerRepository.findAllPublishAnswer(currQuestion.getId());

        return PublishedExam.builder().challenge(challengeResponse).question(publishQuest).answers(publishAnswers).build();

    }



    @Override
    public PageResponse<StudentInChallengeResponse> getAllStudentInChallengeIsLogin(int challengeId) {
        return listConverter.toPageResponse(participantRepository.findAllStudentIsLogin(challengeId, Pageable.unpaged()));
    }


    @Override
    public void openChallenge( int challengeId) throws Exception {

        this.createAllStudentAnswerInChallenge(challengeId);


    }


    private void createAllStudentAnswerInChallenge(int challengeId) throws Exception {
        Optional<Challenge> optional = challengeRepository.findOneById(challengeId);
        Challenge challenge = optional.orElseThrow(() -> new NullPointerException("Challenge not found"));


        List<Customer> customers = studentRepository.findAllStudentInChallenge(challenge.getId());

        List<Question> questions = questRepo.findAllByChallengeId(challenge.getId());

        if (customers.size() == 0 || questions.size() == 0)
            throw new ChallengeException("No customer or question found in challenge id = " + challenge.getId());

        List<StudentAnswer> studentAnswers = new ArrayList<>();

        for (Question quest : questions) {
            List<Answer> answers = quest.getAnswers();
            quest.setAskDate(null);


            for (Answer ans : answers) {
                for (Customer stu : customers) {

                    StudentAnswerId id = StudentAnswerId.builder().customer(stu).answer(ans).challenge(challenge).question(quest).build();

                    studentAnswers.add(StudentAnswer.builder().key(id).score(0d).isCorrect(null).answerDate(null).build());

                }
            }
        }

        questRepo.saveAll(questions);
        studentAnswerRepository.saveAll(studentAnswers);

    }




}
