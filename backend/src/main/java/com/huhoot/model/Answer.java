package com.huhoot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Data
@AllArgsConstructor
@EntityListeners({ AuditingEntityListener.class })
@Where(clause="is_non_deleted=true")
public class Answer extends Auditable{

	@Id
	@GeneratedValue
	private int id;

	@Column(columnDefinition = "nvarchar(255)")
	private String content;

	private boolean isCorrect;

	private boolean isNonDeleted;

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

	@OneToMany(mappedBy = "key.answer", cascade = CascadeType.ALL)
	private List<StudentAnswer> studentAnswers = new ArrayList<>();

	public Answer (){
		this.isNonDeleted = true;
	}

}
