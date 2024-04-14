package com.rishi.ticketmasterchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private String title;
    private String id;
    private String dateStatus;
    private String timeZone;
    private String startDate;
    private List<Artist> artists;
    private Venue venue;
    private Boolean hiddenFromSearch;
}
