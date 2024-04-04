package com.johncross.demo.santanderinterview.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "TRIVIA")
@Data
@ToString
public class TriviaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TRIVIA_ID", unique = true)
    private long triviaId;

    @Column(name="QUESTION")
    private String question;

    @Column(name="CORRECT_ANSWER")
    private String correctAnswer;

    @Column(name = "ANSWER_ATTEMPTS")
    private int answerAttempts;

}
