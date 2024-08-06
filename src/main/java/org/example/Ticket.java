package org.example;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.time.LocalTime;


@AllArgsConstructor
@Getter
@Jacksonized
@Builder
public class Ticket {

    private String origin;
    @JsonProperty("origin_name")
    private String originName;
    private String destination;
    @JsonProperty("destination_name")
    private String destinationName;
    @JsonProperty("departure_date")
    @JsonFormat(pattern = "dd.MM.yy")
    private LocalDate departureDate;
    @JsonProperty("departure_time")
    @JsonFormat(pattern = "H:mm")
    private LocalTime departureTime;
    @JsonProperty("arrival_date")
    @JsonFormat(pattern = "dd.MM.yy")
    private LocalDate arrivalDate;
    @JsonProperty("arrival_time")
    @JsonFormat(pattern = "H:mm")
    private LocalTime arrivalTime;
    private String carrier;
    private Integer stops;
    private Double price;

}
