package com.huhoot.converter;

import com.huhoot.host.manage.question.QuestionAddRequest;
import com.huhoot.model.Question;

public class QuestionConverter {


    public static Question toEntity(QuestionAddRequest request) {
        Question question = new Question();
        question.setContent(request.getQuestionContent());
        question.setImage(request.getQuestionImage());
        question.setAnswerTimeLimit(request.getAnswerTimeLimit());

        return question;
    }


}
