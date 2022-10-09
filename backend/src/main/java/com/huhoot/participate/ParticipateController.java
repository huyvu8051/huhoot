package com.huhoot.participate;

import com.huhoot.model.Customer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/participant")
@AllArgsConstructor
public class ParticipateController {

    private final ParticipateService participateService;

    @PostMapping
    public ResponseEntity<SendAnswerResponse> sendAnswer(@RequestBody StudentAnswerRequest request) throws Exception {

        Customer userDetails = (Customer) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok(participateService.sendAnswer(request, userDetails));

    }

}
