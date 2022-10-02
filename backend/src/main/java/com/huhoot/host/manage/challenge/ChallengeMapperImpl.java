package com.huhoot.host.manage.challenge;

import com.huhoot.auditing.AuditingMapper;
import com.huhoot.dto.ChallengeResponse;
import com.huhoot.model.Challenge;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2021-11-30T22:59:04+0700",
        comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_312 (Temurin)"
)
@Primary
@Component("myChallengeMapper")
@AllArgsConstructor
public class ChallengeMapperImpl implements ChallengeMapper {

    private final AuditingMapper auditingMapper;

    @Override
    public void update(ChallengeUpdateRequest dto, Challenge entity) {
        if (dto == null) {
            return;
        }

        if (dto.getIsNonDeleted() != null) {
            entity.setNonDeleted(dto.getIsNonDeleted());
        }
        entity.setId(dto.getId());
        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }

        if (dto.getChallengeStatus() != null) {
            entity.setChallengeStatus(dto.getChallengeStatus());
        }
    }

    @Override
    public ChallengeResponse toDto(Challenge entity) {
        if (entity == null) {
            return null;
        }

        ChallengeResponse challengeResponse = new ChallengeResponse();

        auditingMapper.setAudit(entity, challengeResponse);

        challengeResponse.setId(entity.getId());
        challengeResponse.setTitle(entity.getTitle());
        challengeResponse.setChallengeStatus(entity.getChallengeStatus());

        return challengeResponse;
    }

    @Override
    public Challenge toEntity(ChallengeAddRequest dto) {
        if (dto == null) {
            return null;
        }

        Challenge challenge = new Challenge();

        challenge.setTitle(dto.getTitle());
        challenge.setChallengeStatus(dto.getChallengeStatus());

        return challenge;
    }

}
