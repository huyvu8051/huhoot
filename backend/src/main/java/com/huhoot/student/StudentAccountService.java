package com.huhoot.student;

import com.huhoot.dto.ChangePasswordRequest;
import com.huhoot.exception.AccountException;
import com.huhoot.model.Customer;

public interface StudentAccountService {

    void changePassword(ChangePasswordRequest request, Customer user) throws AccountException;
}
