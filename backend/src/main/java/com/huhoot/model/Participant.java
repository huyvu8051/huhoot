package com.huhoot.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@AssociationOverrides({
        @AssociationOverride(name = "key.customer", joinColumns = @JoinColumn(name = "customer_id")),
        @AssociationOverride(name = "key.challenge", joinColumns = @JoinColumn(name = "challenge_id"))})
@EntityListeners({AuditingEntityListener.class})
public class Participant extends Auditable {


    private boolean isLogin;

    @EmbeddedId
    private ParticipantId key = new ParticipantId();

    private int totalScore;

    private boolean isKicked;

    private boolean isOnline;

    private boolean isNonDeleted;

    public Participant(Customer customer, Challenge challenge) {
        this.setCustomer(customer);
        this.setChallenge(challenge);
        this.isLogin = false;
        this.totalScore = 0;
        this.isKicked = false;
        this.isOnline = false;
        this.isNonDeleted = true;
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

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }


}