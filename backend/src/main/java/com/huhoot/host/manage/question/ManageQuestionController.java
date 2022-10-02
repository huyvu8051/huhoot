package com.huhoot.host.manage.question;

import com.huhoot.exception.NotYourOwnException;
import com.huhoot.model.Customer;
import com.huhoot.vue.vdatatable.paging.PageResponse;
import com.huhoot.vue.vdatatable.paging.VDataTablePagingConverter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("host")
@AllArgsConstructor
public class ManageQuestionController {

    private final ManageQuestionService manageQuestionService;

    private final VDataTablePagingConverter vDataTablePagingConverter;


    @PostMapping("/question/findAll")
    public ResponseEntity<PageResponse<QuestionResponse>> findAll(@RequestBody FindAllQuestionRequest request) {
        Customer userDetails = (Customer) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        Pageable pageable = vDataTablePagingConverter.toPageable(request);
        PageResponse<QuestionResponse> response = manageQuestionService.findAllQuestionInChallenge(request.getChallengeId(), pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/question")
    public ResponseEntity<QuestionResponse> add(@Valid @RequestBody QuestionAddRequest request) throws IOException, NullPointerException, NotYourOwnException {

        Customer userDetails = (Customer) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok(manageQuestionService.addOneQuestion(userDetails, request));

    }


    @PatchMapping("/question")
    public void update(@RequestBody QuestionUpdateRequest request) throws NotYourOwnException, NullPointerException {
        Customer userDetails = (Customer) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        manageQuestionService.updateOneQuestion(userDetails, request);


    }

    @PatchMapping("/question/ordinal")
    public void updateOrdinal(@RequestBody QuestionOrdinalUpdateRequest request) throws NotYourOwnException, NullPointerException {
        Customer userDetails = (Customer) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        manageQuestionService.updateOrdinal(userDetails, request);

    }


}
