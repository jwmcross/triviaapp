package com.johncross.demo.santanderinterview.handler;

import com.johncross.demo.santanderinterview.dto.response.ResultResponse;
import com.johncross.demo.santanderinterview.entity.TriviaEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TriviaHandler {

    private final static String CORRECT_ANSWER = "right!";
    private final static String WRONG_ANSWER = "wrong!";
    private final static String MAX_ATTEMPTS_EXCEEDED = "Max attempts reached!";
    private final static int MAX_ATTEMPTS_ALLOWED = 3;

    private TriviaEntity triviaEntity;
    private String answer;

    public TriviaHandler(TriviaEntity triviaEntity, String answer) {
        this.triviaEntity = triviaEntity;
        this.answer = answer;
    }

    public ResponseEntity<ResultResponse> handleAnswer() {

        if (checkMaxAttempts()) {
            ResultResponse response = ResultResponse.builder()
                    .result(MAX_ATTEMPTS_EXCEEDED)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        return checkAnswer();
    }


    private ResponseEntity<ResultResponse> checkAnswer() {
        String correctAnswer = triviaEntity.getCorrectAnswer();

        if (answer.equalsIgnoreCase(correctAnswer)) {
            ResultResponse response = ResultResponse.builder()
                    .result(CORRECT_ANSWER)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResultResponse response = ResultResponse.builder()
                    .result(WRONG_ANSWER)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    private boolean checkMaxAttempts() {
        return triviaEntity.getAnswerAttempts() > MAX_ATTEMPTS_ALLOWED;
    }
}
