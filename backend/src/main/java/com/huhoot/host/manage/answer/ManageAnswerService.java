package com.huhoot.host.manage.answer;

import com.huhoot.model.Customer;
import com.huhoot.organize.PublishAnswer;
import com.huhoot.vue.vdatatable.paging.PageResponse;
import org.springframework.data.domain.Pageable;

public interface ManageAnswerService {

    PageResponse<PublishAnswer> findAllAnswerByQuestionId(Customer userDetails, int questionId, Pageable pageable);

    void addOneAnswer(Customer userDetails, AnswerAddRequest request) throws Exception;

    void updateOneAnswer(Customer userDetails, AnswerUpdateRequest request);

    void updateOrdinal(Customer userDetails, AnswerOrdinalUpdateRequest request);
}
