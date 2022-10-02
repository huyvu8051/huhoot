package com.huhoot.dto;

import lombok.Data;

@Data
public class StudentInChallengeUpdateRequest {
    private int studentId;
    private int challengeId;
    private Boolean isKicked;
    private Boolean isNonDeleted;
}