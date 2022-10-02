package com.huhoot.host.manage.question;

import com.huhoot.auditing.AuditableDto;
import com.huhoot.enums.AnswerOption;
import com.huhoot.enums.Points;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionResponse extends AuditableDto {
    private int id;
    private int ordinalNumber;
    private String questionContent;
    private String questionImage;
    private int answerTimeLimit;
    private Points point;
    private AnswerOption answerOption;
    private Long askDate;
    private boolean isNonDeleted;

    public QuestionResponse() {
        super();
    }

    public QuestionResponse(int id, int ordinalNumber, String questionContent, String questionImage,
                            int answerTimeLimit, Long askDate,
                            boolean isNonDeleted, Long createdDate, String createdBy, Long modifiedDate, String modifiedBy) {
        super(createdDate, createdBy, modifiedDate, modifiedBy);
        this.id = id;
        this.ordinalNumber = ordinalNumber;
        this.questionContent = questionContent;
        this.questionImage = questionImage;
        this.answerTimeLimit = answerTimeLimit;
        if (askDate != null) {
            this.askDate = askDate;
        }
        this.isNonDeleted = isNonDeleted;
    }


}
