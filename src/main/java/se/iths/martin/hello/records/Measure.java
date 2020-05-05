package se.iths.martin.hello.records;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record Measure(@JsonProperty("time")LocalDateTime time, @JsonProperty("value")String value){}
