package com.rishi.ticketmasterchallenge.controller;

import com.rishi.ticketmasterchallenge.dto.Artist;
import com.rishi.ticketmasterchallenge.dto.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtistInfoDetailResponse {
    private Artist artist;
    private List<Event> events;
}
