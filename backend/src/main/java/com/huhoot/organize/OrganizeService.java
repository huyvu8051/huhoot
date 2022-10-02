package com.huhoot.organize;

import com.corundumstudio.socketio.SocketIOClient;
import com.huhoot.exception.NoClientInBroadcastOperations;
import com.huhoot.model.Challenge;
import com.huhoot.model.Customer;
import com.huhoot.model.Question;
import com.huhoot.vue.vdatatable.paging.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrganizeService {

    void openChallenge(Customer userDetails, int id) throws Exception;

    List<StudentInChallengeResponse> getAllStudentInChallengeIsLogin(Customer userDetails, int challengeId);

    /**
     * Start challenge, update challenge status to IN_PROGRESS
     *
     * @param challengeId challenge id
     * @param adminId     admin id
     * @return List of QuestionResponse
     */
    void startChallenge(int challengeId, int adminId);


    /**
     * Sent all Answer to all {@link SocketIOClient} in {@link com.corundumstudio.socketio.BroadcastOperations}
     *

     * @throws NullPointerException not found exception
     */
    void showCorrectAnswer(Question question) throws NullPointerException;
    PageResponse showCorrectAnswer(int challengeId) throws NullPointerException;

    /**
     * @param challengeId {@link Challenge} id
     * @param pageable    {@link Pageable}
     * @return List of top 20 customer have best total challenge score
     */
    PageResponse<StudentScoreResponse> getTopStudent(int challengeId, Pageable pageable);


    /**
     * Set challenge status ENDED and sent endChallenge event to all Client in Room
     *
     * @param challengeId {@link Challenge} id
     * @throws NullPointerException
     */
    void endChallenge(int challengeId) throws NullPointerException;


    /**
     * @param studentIds  List of {@link Customer} ids
     * @param challengeId {@link Challenge} id
     * @param adminId     {@link Customer} id
     */
    void kickStudent(List<Integer> studentIds, int challengeId, int adminId);

    void publishNextQuestion(int challengeId) throws Exception;


    void findAnyClientAndEnableAutoOrganize(int challengeId) throws NoClientInBroadcastOperations;
    void disableAutoOrganize(int challengeId);

    void updateChallengeStatusToClient(int challengeId);

    PageResponse<StudentInChallengeResponse> getAllStudentInChallengeIsLogin(int challengeId);
}
