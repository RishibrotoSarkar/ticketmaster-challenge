package com.rishi.ticketmasterchallenge.client;

import com.rishi.ticketmasterchallenge.dto.Artist;
import com.rishi.ticketmasterchallenge.dto.Event;
import com.rishi.ticketmasterchallenge.dto.Venue;

import java.util.List;

public interface ConcertInfoClient {
    Artist getArtistById(String id);
    List<Venue> getVenues();
    List<Event> getEventsByArtistId(String artistId);
}
