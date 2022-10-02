package com.huhoot.converter;

import com.huhoot.host.manage.answer.AnswerAddRequest;
import com.huhoot.model.Answer;

public class AnswerConverter {


    public static Answer toEntity(AnswerAddRequest request) {
        Answer answer = new Answer();
        answer.setContent(request.getAnswerContent());
        answer.setCorrect(request.isCorrect());
        return answer;
    }


}
