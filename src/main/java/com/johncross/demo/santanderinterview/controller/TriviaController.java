package com.johncross.demo.santanderinterview.controller;

import com.johncross.demo.santanderinterview.dto.request.Answer;
import com.johncross.demo.santanderinterview.dto.response.ResultResponse;
import com.johncross.demo.santanderinterview.dto.response.TriviaResponse;
import com.johncross.demo.santanderinterview.service.TriviaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trivia")
public class TriviaController {

    private final TriviaService triviaService;

    public TriviaController(TriviaService triviaService) {
        this.triviaService = triviaService;
    }

    @PostMapping("/start")
    public ResponseEntity<TriviaResponse> startTrivia() {
        TriviaResponse triviaResponse = triviaService.getTriviaQuestion();
        return ResponseEntity.ok(triviaResponse);
    }

    @PutMapping(value ="/reply/{id}",  produces = {"application/json"}, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> reply(@RequestBody Answer answer, @PathVariable("id") long id) {

        return this.triviaService.answerTrivia(answer, id);
    }

}
