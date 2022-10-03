package com.huhoot.host.manage.question;

import com.huhoot.enums.Points;
import com.huhoot.enums.TimeLimit;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class QuestionAddRequest {

    private int challengeId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String questionContent;

    private String questionImage;

    @NotNull
    @Min(5)
    private TimeLimit timeLimit;

    @NotNull
    private Points point;

}
