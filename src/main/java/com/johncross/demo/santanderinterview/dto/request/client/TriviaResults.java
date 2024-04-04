package com.johncross.demo.santanderinterview.dto.request.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TriviaResults {
    private String type;
    private String difficulty;
    private String category;
    private String question;
    @JsonProperty(value = "correct_answer")
    private String correctAnswer;
    @JsonProperty(value = "incorrect_answers")
    private List<String> incorrectAnswers;
}
