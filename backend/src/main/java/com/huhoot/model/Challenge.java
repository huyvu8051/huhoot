package com.huhoot.model;

import com.huhoot.enums.ChallengeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@Where(clause = "is_non_deleted=true")
public class Challenge extends Auditable {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String title;

    private ChallengeStatus challengeStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "challenge")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "key.challenge")
    private List<StudentAnswer> studentAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "key.challenge")
    private List<Participant> participants = new ArrayList<>();

    private boolean isNonDeleted;

    public Challenge() {
        this.isNonDeleted = true;
    }

    public Challenge(int id) {
        this.id = id;
    }


}
