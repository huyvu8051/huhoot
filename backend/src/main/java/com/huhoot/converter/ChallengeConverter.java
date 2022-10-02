package com.huhoot.converter;

import com.huhoot.enums.ChallengeStatus;
import com.huhoot.host.manage.challenge.ChallengeAddRequest;
import com.huhoot.dto.ChallengeResponse;
import com.huhoot.model.Challenge;
import org.springframework.stereotype.Component;

@Component
public class ChallengeConverter {

    public static ChallengeResponse toChallengeResponse(Challenge entity) {
        ChallengeResponse response = new ChallengeResponse();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setChallengeStatus(entity.getChallengeStatus());
        response.setOwner(entity.getCustomer().getUsername());
        if(entity.getCreatedDate() != null){
            response.setCreatedDate(entity.getCreatedDate());
        }
        if(entity.getModifiedDate() != null){
            response.setModifiedDate(entity.getModifiedDate());
        }
        response.setCreatedBy(entity.getCreatedBy());

        response.setModifiedBy(entity.getModifiedBy());
        return response;
    }

    public static Challenge toEntity(ChallengeAddRequest request) {
        Challenge challenge = new Challenge();
        challenge.setTitle(request.getTitle());
        // challenge.setCoverImage(request.getCoverImage());
        challenge.setChallengeStatus(ChallengeStatus.WAITING);
        return challenge;
    }
}
