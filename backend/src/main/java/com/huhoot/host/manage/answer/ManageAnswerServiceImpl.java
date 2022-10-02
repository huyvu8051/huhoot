package com.huhoot.host.manage.answer;

import com.huhoot.converter.AnswerConverter;
import com.huhoot.converter.ListConverter;
import com.huhoot.model.Customer;
import com.huhoot.organize.PublishAnswer;
import com.huhoot.mapper.AnswerMapper;
import com.huhoot.model.Answer;
import com.huhoot.model.Question;
import com.huhoot.repository.AnswerRepository;
import com.huhoot.repository.QuestionRepository;
import com.huhoot.vue.vdatatable.paging.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManageAnswerServiceImpl implements ManageAnswerService {
    private final AnswerRepository answerRepository;
    private final ListConverter listConverter;
    private final QuestionRepository questionRepository;

    @Override
    public PageResponse<PublishAnswer> findAllAnswerByQuestionId(Customer userDetails, int questionId, Pageable pageable) {
        Page<PublishAnswer> answers = answerRepository.findAllByQuestionIdAndAdminId(questionId, userDetails.getId(), pageable);

        return listConverter.toPageResponse(answers);
    }


    @Override
    public void addOneAnswer(Customer userDetails, AnswerAddRequest request) throws Exception {
        Optional<Question> optional = questionRepository.findOneById(request.getQuestionId());

        Question question = optional.orElseThrow(() -> new NullPointerException("Challenge not found"));

        Answer answer = AnswerConverter.toEntity(request);
        answer.setQuestion(question);
        answerRepository.save(answer);

    }

    private final AnswerMapper answerMapper;

    @Override
    public void updateOneAnswer(Customer userDetails, AnswerUpdateRequest request) {
        Optional<Answer> optional = answerRepository.findOneById(request.getId());

        Answer answer = optional.orElseThrow(() -> new NullPointerException("Challenge not found"));

        answerMapper.updateAnswer(request, answer);

        answerRepository.save(answer);
    }

    @Override
    public void updateOrdinal(Customer userDetails, AnswerOrdinalUpdateRequest request) {
        List<AnswerOrdinal> list = request.getList();
        List<Answer> result = new ArrayList<>();

        for (AnswerOrdinal dto : list) {
            Optional<Answer> optional = answerRepository.findOneById(dto.getId());
            Answer entity = optional.orElseThrow(() -> new NullPointerException("Question not found"));
            result.add(entity);
        }

        answerRepository.saveAll(result);
    }
}
