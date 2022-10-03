package com.huhoot.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@AssociationOverrides({
        @AssociationOverride(name = "key.customer", joinColumns = @JoinColumn(name = "customer_id")),
        @AssociationOverride(name = "key.challenge", joinColumns = @JoinColumn(name = "challenge_id"))})
@EntityListeners({AuditingEntityListener.class})
public class Participant extends Auditable {
    @EmbeddedId
    private ParticipantId key = new ParticipantId();

    private int totalScore;

    private boolean isOnline;

    private boolean isNonDeleted;

    public Participant(Customer customer, Challenge challenge) {
        this.setCustomer(customer);
        this.setChallenge(challenge);
        this.totalScore = 0;
        this.isOnline = true;
        this.isNonDeleted = true;
    }

    public Participant(){
        isNonDeleted = true;
    }


    @Transient
    public Customer getCustomer() {
        return getKey().getCustomer();
    }

    public void setCustomer(Customer customer) {
        getKey().setCustomer(customer);
    }

    @Transient
    public Challenge getChallenge() {
        return key.getChallenge();
    }

    public void setChallenge(Challenge challenge) {
        getKey().setChallenge(challenge);
    }



}