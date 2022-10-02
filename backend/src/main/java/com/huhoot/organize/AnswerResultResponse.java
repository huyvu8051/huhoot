package com.huhoot.organize;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AnswerResultResponse {
    private int id;
    private String content;

    public AnswerResultResponse(int id, String content) {
        this.id = id;
        this.content = content;
    }
}
