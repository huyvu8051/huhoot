package com.huhoot.converter;

import com.huhoot.host.manage.studentInChallenge.StudentInChallengeResponse;
import com.huhoot.model.Customer;
import com.huhoot.model.Participant;

public class StudentInChallengeConverter {

    public static StudentInChallengeResponse toStudentChallengeResponse(Participant entity){
        StudentInChallengeResponse response = new StudentInChallengeResponse();

        Customer customer = entity.getCustomer();

        response.setStudentId(customer.getId());
        response.setStudentUsername(customer.getUsername());
        response.setStudentFullName(customer.getFullName());
        response.setIsLogin(entity.isLogin());
        response.setIsKicked(entity.isKicked());
        response.setIsOnline(entity.isOnline());
        return response;
    }
}
