package com.huhoot.host.manage.challenge;

import com.huhoot.dto.ChallengeResponse;
import com.huhoot.exception.NotYourOwnException;
import com.huhoot.model.Challenge;
import com.huhoot.model.Customer;
import com.huhoot.vue.vdatatable.paging.PageResponse;
import com.huhoot.vue.vdatatable.paging.VDataTablePagingConverter;
import com.huhoot.vue.vdatatable.paging.VDataTablePagingRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/organizer")
public class ManageChallengeController {
    private final ManageChallengeService manageChallengeService;

    private final VDataTablePagingConverter vDataTablePagingConverter;


    @GetMapping("/challenge")
    public PageResponse<ChallengeResponse> findAll(VDataTablePagingRequest request) {

        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        Pageable pageable = vDataTablePagingConverter.toPageable(request);

        return manageChallengeService.findAllOwnChallenge(customer.getId(), pageable);
    }

    @GetMapping("/participate")
    public PageResponse<ChallengeResponse> findAllParticipate(VDataTablePagingRequest request) {

        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        Pageable pageable = vDataTablePagingConverter.toPageable(request);

        return manageChallengeService.findAllParticipate(customer.getId(), pageable);
    }


    @PostMapping("/challenge")
    public ResponseEntity<ChallengeResponse> add(@Valid @RequestBody ChallengeAddRequest request) throws IOException {

        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok(manageChallengeService.addOneChallenge(customer, request));

    }

    @PatchMapping("/challenge")
    public void update(@Valid @RequestBody ChallengeUpdateRequest request) throws NotYourOwnException, NullPointerException {

        Challenge c = manageChallengeService.findChallenge(request.getId());

        manageChallengeService.updateOneChallenge(request, c);

    }


}
