package com.johncross.demo.santanderinterview.service;

import com.johncross.demo.santanderinterview.client.TriviaClient;
import com.johncross.demo.santanderinterview.dto.request.Answer;
import com.johncross.demo.santanderinterview.dto.response.ResultResponse;
import com.johncross.demo.santanderinterview.dto.response.TriviaResponse;
import com.johncross.demo.santanderinterview.dto.request.client.TriviaData;
import com.johncross.demo.santanderinterview.dto.request.client.TriviaResults;
import com.johncross.demo.santanderinterview.entity.TriviaEntity;
import com.johncross.demo.santanderinterview.exception.NotFoundException;
import com.johncross.demo.santanderinterview.handler.TriviaHandler;
import com.johncross.demo.santanderinterview.repository.TriviaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TriviaService {

    private final TriviaRepository triviaRepository;
    private final TriviaClient triviaClient;

    public TriviaService(TriviaRepository triviaRepository, TriviaClient triviaClient) {
        this.triviaRepository = triviaRepository;
        this.triviaClient = triviaClient;
    }
    public TriviaResponse getTriviaQuestion() {

        TriviaData triviaData =  triviaClient.getTrivia();

        TriviaEntity triviaEntity = mapTriviaDtoToEntity(triviaData);

        this.triviaRepository.save(triviaEntity);

        return buildTriviaResponse(triviaEntity, triviaData);
    }

    private TriviaEntity mapTriviaDtoToEntity(TriviaData trivia) {
        TriviaResults data = trivia.getResults().get(0);

        TriviaEntity entity = new TriviaEntity();
        entity.setQuestion(data.getQuestion());
        entity.setCorrectAnswer(data.getCorrectAnswer());

        return entity;
    }

    private TriviaResponse buildTriviaResponse(TriviaEntity entity, TriviaData data) {
        List<String> possibleAnswers = new ArrayList<>();

        Iterable<String> iterable = data.getResults()
                .get(0).getIncorrectAnswers();
        iterable.forEach(possibleAnswers::add);
        possibleAnswers.add(entity.getCorrectAnswer());

        Collections.shuffle(possibleAnswers);

        TriviaResponse response = TriviaResponse.builder()
                .triviaId(entity.getTriviaId())
                .possibleAnswers(possibleAnswers)
                .build();

        return response;
    }
    public ResponseEntity<ResultResponse> answerTrivia(Answer answer, long id) {
        Optional<TriviaEntity> trivia = this.triviaRepository.findById(id);
        if (trivia.isEmpty()) {
            throw new NotFoundException("No such question!");
        } else {
            TriviaEntity entity = incrementAttempt(trivia.get());
            TriviaHandler handler = new TriviaHandler(entity, answer.getAnswer());
            return handler.handleAnswer();
        }
    }

    private TriviaEntity incrementAttempt(TriviaEntity triviaEntity) {
        int counter = triviaEntity.getAnswerAttempts();
        triviaEntity.setAnswerAttempts(counter + 1);
        return this.triviaRepository.save(triviaEntity);
    }

}