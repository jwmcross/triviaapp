

import com.johncross.demo.santanderinterview.client.TriviaClient;
import com.johncross.demo.santanderinterview.dto.request.Answer;
import com.johncross.demo.santanderinterview.dto.request.client.TriviaData;
import com.johncross.demo.santanderinterview.dto.request.client.TriviaResults;
import com.johncross.demo.santanderinterview.dto.response.ResultResponse;
import com.johncross.demo.santanderinterview.dto.response.TriviaResponse;
import com.johncross.demo.santanderinterview.entity.TriviaEntity;
import com.johncross.demo.santanderinterview.repository.TriviaRepository;
import com.johncross.demo.santanderinterview.service.TriviaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TriviaServiceTest {
    private TriviaService triviaService;
    @Mock
    private TriviaRepository triviaRepository;
    @Mock
    private TriviaClient triviaClient;

    @BeforeEach
    void setup(){
        triviaService = new TriviaService(triviaRepository, triviaClient);
    }

    @Test
    void getTriviaQuestion() {
        // Given
        String correctAnswer = "England";
        String question = "Which football team won the World Cup 2024?";

        TriviaData triviaData = TriviaData.builder()
                .responseCode(0)
                .results(List.of(
                        TriviaResults.builder()
                            .category("Sports")
                            .type("multiple")
                            .difficulty("hard")
                            .question(question)
                            .correctAnswer(correctAnswer)
                            .incorrectAnswers(Arrays.asList("Brazil", "France", "Spain"))
                        .build())
                )
                .build();

        TriviaEntity entity = new TriviaEntity();
        entity.setTriviaId(1L);
        entity.setQuestion(question);
        entity.setCorrectAnswer(correctAnswer);

        TriviaResponse expectedResponse = TriviaResponse.builder()
                .triviaId(1L)
                .possibleAnswers(Arrays.asList("Brazil", "France", "Spain", "England"))
                .build();

        // When
        when(triviaClient.getTrivia()).thenReturn(triviaData);
        when(triviaRepository.save(any(TriviaEntity.class))).thenReturn(entity);
        TriviaResponse actualResponse = triviaService.getTriviaQuestion();

        //Then
        Assertions.assertTrue(actualResponse.getPossibleAnswers().contains(correctAnswer));
    }

}
