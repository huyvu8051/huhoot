package com.huhoot.customer.manage;

import com.huhoot.dto.ChallengeResponse;
import com.huhoot.model.Customer;
import com.huhoot.vue.vdatatable.paging.PageResponse;
import org.springframework.data.domain.Pageable;

public interface StudentManageService {
    PageResponse<ChallengeResponse> findAllChallenge(Customer userDetails, Pageable pageable);
}
