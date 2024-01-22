package com.voting.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VoteDto {
    @JsonProperty(value = "PK")
    private String PK;
    @JsonProperty(value = "B")
    private String B;
    @JsonProperty(value = "C")
    private String C;
    @JsonProperty(value = "R")
    private String R;
    @JsonProperty(value = "proposalId")
    private String proposalId;
    @JsonProperty(value = "content")
    private String content;
}
