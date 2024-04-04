package com.johncross.demo.santanderinterview.client;

import com.johncross.demo.santanderinterview.dto.request.client.TriviaData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class TriviaClient {

    private final RestClient restClient;
    private final static String URL = "https://opentdb.com/api.php?amount=1";

    public TriviaClient() {
        this.restClient = RestClient.create();
    }

    public TriviaData getTrivia() {

        TriviaData result = restClient.get()
                .uri(URL)
                .retrieve()
                .body(TriviaData.class);

        return result;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return headers;
    }

}
