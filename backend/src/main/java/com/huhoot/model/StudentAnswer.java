package com.huhoot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@AssociationOverrides({
        @AssociationOverride(name = "key.customer", joinColumns = @JoinColumn(name = "student_id")),
        @AssociationOverride(name = "key.question", joinColumns = @JoinColumn(name = "question_id")),
        @AssociationOverride(name = "key.answer", joinColumns = @JoinColumn(name = "answer_id")),
        @AssociationOverride(name = "key.challenge", joinColumns = @JoinColumn(name = "challenge_id"))})
@EntityListeners({AuditingEntityListener.class})
@SuperBuilder
public class StudentAnswer extends Auditable {
    @EmbeddedId
    @Getter
    private StudentAnswerId key = new StudentAnswerId();

    @Getter
    @Setter
    private Double score;

    @Getter
    @Setter
    private Boolean isCorrect;

    @Getter
    @Setter
    private Long answerDate;

    public StudentAnswer() {
        super();
    }


    @Transient
    public Customer getStudent() {
        return getKey().getCustomer();
    }

    public void setStudent(Customer customer) {
        getKey().setCustomer(customer);
    }

    public void setAnswer(Answer ans) {
        key.setAnswer(ans);
    }

    public void setChallenge(Challenge chal) {
        key.setChallenge(chal);
    }

    @Transient
    public Question getQuestion() {
        return getKey().getQuestion();
    }

    public void setQuestion(Question question) {
        getKey().setQuestion(question);
    }


    @Transient
    public Answer getAnswer() {
        return key.getAnswer();
    }

    @Transient
    public Challenge getChallenge(){
        return key.getChallenge();
    }


}
