package com.huhoot.organize;

import com.huhoot.auditing.AuditableDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentInChallengeResponse extends AuditableDto {
    private int studentId;
    private String studentUsername;
    private String studentFullName;

    private Boolean isOnline;


    private Boolean isNonDeleted;

    public StudentInChallengeResponse(int studentId, String studentUsername, String studentFullName, Boolean isOnline, String createdBy, Long createdDate, String modifiedBy, Long modifiedDate, Boolean isNonDeleted) {
        super(createdDate, createdBy, modifiedDate, modifiedBy);
        this.studentId = studentId;
        this.studentUsername = studentUsername;
        this.studentFullName = studentFullName;
        this.isOnline = isOnline;
        this.isNonDeleted = isNonDeleted;
    }
}