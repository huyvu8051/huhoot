package com.huhoot.converter;

import com.huhoot.model.Customer;
import com.huhoot.model.Participant;
import com.huhoot.organize.StudentInChallengeResponse;

public class StudentInChallengeConverter {

    public static StudentInChallengeResponse toStudentChallengeResponse(Participant entity) {
        StudentInChallengeResponse response = new StudentInChallengeResponse();

        Customer customer = entity.getCustomer();

        response.setStudentId(customer.getId());
        response.setStudentUsername(customer.getUsername());
        response.setStudentFullName(customer.getFullName());
        response.setIsOnline(entity.isOnline());
        return response;
    }
}
