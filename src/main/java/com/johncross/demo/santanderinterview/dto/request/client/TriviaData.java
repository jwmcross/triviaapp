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
public class TriviaData {
    private int responseCode;
    @JsonProperty(value = "results")
    private List<TriviaResults> results;
}
