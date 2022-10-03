package com.huhoot.mapper.impl;

import com.huhoot.dto.StudentInChallengeUpdateRequest;
import com.huhoot.mapper.StudentInChallengeMapper;
import com.huhoot.model.Customer;
import com.huhoot.model.Participant;
import com.huhoot.model.ParticipantId;
import com.huhoot.organize.StudentInChallengeResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2021-11-30T23:13:30+0700",
        comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_312 (Temurin)"
)
@Primary
@Component("myStudentInChallengeMapper")
public class StudentInChallengeMapperImpl implements StudentInChallengeMapper {

    @Override
    public StudentInChallengeResponse toDto(Participant entity) {
        if (entity == null) {
            return null;
        }

        StudentInChallengeResponse studentInChallengeResponse = new StudentInChallengeResponse();

        studentInChallengeResponse.setStudentId(entityPrimaryKeyStudentId(entity));
        studentInChallengeResponse.setStudentUsername(entityPrimaryKeyStudentUsername(entity));
        studentInChallengeResponse.setStudentFullName(entityPrimaryKeyStudentFullName(entity));

        studentInChallengeResponse.setIsOnline(entity.isOnline());
        studentInChallengeResponse.setCreatedBy(entity.getCreatedBy());
        studentInChallengeResponse.setCreatedDate(entity.getCreatedDate());
        studentInChallengeResponse.setModifiedBy(entity.getModifiedBy());
        studentInChallengeResponse.setModifiedDate(entity.getModifiedDate());
        studentInChallengeResponse.setIsNonDeleted(entity.isNonDeleted());

        return studentInChallengeResponse;
    }

    @Override
    public void update(StudentInChallengeUpdateRequest dto, Participant entity) {
        if (dto == null) {
            return;
        }

        if (dto.getIsNonDeleted() != null) {
            entity.setNonDeleted(dto.getIsNonDeleted());
        }

    }

    private int entityPrimaryKeyStudentId(Participant participant) {
        if (participant == null) {
            return 0;
        }
        ParticipantId primaryKey = participant.getKey();
        if (primaryKey == null) {
            return 0;
        }
        Customer customer = primaryKey.getCustomer();
        if (customer == null) {
            return 0;
        }
        int id = customer.getId();
        return id;
    }

    private String entityPrimaryKeyStudentUsername(Participant participant) {
        if (participant == null) {
            return null;
        }
        ParticipantId primaryKey = participant.getKey();
        if (primaryKey == null) {
            return null;
        }
        Customer customer = primaryKey.getCustomer();
        if (customer == null) {
            return null;
        }
        String username = customer.getUsername();
        if (username == null) {
            return null;
        }
        return username;
    }

    private String entityPrimaryKeyStudentFullName(Participant participant) {
        if (participant == null) {
            return null;
        }
        ParticipantId primaryKey = participant.getKey();
        if (primaryKey == null) {
            return null;
        }
        Customer customer = primaryKey.getCustomer();
        if (customer == null) {
            return null;
        }
        String fullName = customer.getFullName();
        if (fullName == null) {
            return null;
        }
        return fullName;
    }
}
