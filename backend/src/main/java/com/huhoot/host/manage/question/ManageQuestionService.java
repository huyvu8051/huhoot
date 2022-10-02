package com.huhoot.host.manage.question;

import com.huhoot.exception.NotYourOwnException;
import com.huhoot.model.Customer;
import com.huhoot.vue.vdatatable.paging.PageResponse;
import org.springframework.data.domain.Pageable;

public interface ManageQuestionService {

    PageResponse<QuestionResponse> findAllQuestionInChallenge(int challengeId, Pageable pageable);

    QuestionResponse addOneQuestion(Customer userDetails, QuestionAddRequest request) throws NullPointerException, NotYourOwnException;

    void updateOneQuestion(Customer userDetails, QuestionUpdateRequest request) throws NotYourOwnException, NullPointerException;


    void updateOrdinal(Customer userDetails, QuestionOrdinalUpdateRequest request);
}
