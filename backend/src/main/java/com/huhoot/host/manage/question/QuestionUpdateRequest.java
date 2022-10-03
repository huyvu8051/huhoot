package com.huhoot.host.manage.question;

import com.huhoot.enums.TimeLimit;
import com.huhoot.enums.Points;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionUpdateRequest {
    private int id;
    private Integer ordinalNumber;
    private String questionContent;
    private String questionImage;
    private TimeLimit timeLimit;
    private Points point;
    private Boolean isNonDeleted;

}
