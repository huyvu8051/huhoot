package com.huhoot.participate;

import com.corundumstudio.socketio.SocketIOClient;
import com.huhoot.exception.ChallengeException;
import com.huhoot.model.Customer;
import com.huhoot.organize.PublishedExam;

public interface ParticipateService {

    void join(SocketIOClient client, int challengeId, Customer customer) throws ChallengeException;

    SendAnswerResponse sendAnswer(StudentAnswerRequest request, Customer userDetails) throws Exception;

    PublishedExam getCurrentPublishedExam(int challengeId);
}
