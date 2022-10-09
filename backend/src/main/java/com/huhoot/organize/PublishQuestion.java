package com.huhoot.organize;

import com.huhoot.enums.Points;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublishQuestion {
    private int id;
    private int ordinalNumber;
    private String content;
    private String image;
    private int timeLimit;

    private long askDate;
    private long timeout;

    private Points point;

    private int challengeId;

    private int totalQuestion;

    private int questionOrder;

    private boolean theLastQuestion;

    public PublishQuestion(int id, int ordinalNumber, String questionContent, String questionImage, int timeLimit, Points point, int challengeId, int totalQuestion) {
        this.id = id;
        this.ordinalNumber = ordinalNumber;
        this.content = questionContent;
        this.timeLimit = timeLimit;

        this.image = questionImage;
        this.point = point;
        this.challengeId = challengeId;
        this.totalQuestion = totalQuestion;
    }
    public PublishQuestion(int id, int ordinalNumber, String questionContent, String questionImage, int timeLimit, Points point, Long askDate, int challengeId, int totalQuestion) {
        this.id = id;
        this.ordinalNumber = ordinalNumber;
        this.content = questionContent;
        this.timeLimit = timeLimit;
        this.askDate = askDate;
        this.image = questionImage;
        this.point = point;
        this.challengeId = challengeId;
        this.totalQuestion = totalQuestion;
    }

}
