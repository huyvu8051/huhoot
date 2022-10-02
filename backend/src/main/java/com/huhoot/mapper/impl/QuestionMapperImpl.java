package com.huhoot.mapper.impl;

import com.huhoot.host.manage.question.QuestionResponse;
import com.huhoot.host.manage.question.QuestionUpdateRequest;
import com.huhoot.mapper.QuestionMapper;
import com.huhoot.model.Question;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2021-11-30T22:59:36+0700",
        comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_312 (Temurin)"
)
@Primary
@Component("myQuestionMapper")
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public void update(QuestionUpdateRequest dto, Question entity) {
        if (dto == null) {
            return;
        }

        if (dto.getIsNonDeleted() != null) {
            entity.setNonDeleted(dto.getIsNonDeleted());
        }
        entity.setId(dto.getId());
        if (dto.getOrdinalNumber() != null) {
            entity.setOrdinalNumber(dto.getOrdinalNumber());
        }
        if (dto.getQuestionContent() != null) {
            entity.setContent(dto.getQuestionContent());
        }
        if (dto.getQuestionImage() != null) {
            entity.setImage(dto.getQuestionImage());
        }
        if (dto.getAnswerTimeLimit() != null) {
            entity.setAnswerTimeLimit(dto.getAnswerTimeLimit());
        }
    }

    @Override
    public QuestionResponse toDto(Question entity) {
        if (entity == null) {
            return null;
        }

        QuestionResponse questionResponse = new QuestionResponse();

        questionResponse.setId(entity.getId());
        questionResponse.setOrdinalNumber(entity.getOrdinalNumber());
        questionResponse.setQuestionContent(entity.getContent());
        questionResponse.setQuestionImage(entity.getImage());
        questionResponse.setAnswerTimeLimit(entity.getAnswerTimeLimit());
        if (entity.getAskDate() != null) {
            questionResponse.setAskDate(entity.getAskDate());
        }

        questionResponse.setNonDeleted(entity.isNonDeleted());
        if (entity.getCreatedDate() != null) {
            questionResponse.setCreatedDate(entity.getCreatedDate());
        }
        return questionResponse;
    }
}
