package com.rishi.ticketmasterchallenge.service;

import com.rishi.ticketmasterchallenge.client.ConcertInfoClient;
import com.rishi.ticketmasterchallenge.controller.ArtistInfoDetailResponse;
import com.rishi.ticketmasterchallenge.dto.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistInfoServiceImpl implements ArtistInfoService{

    private final ConcertInfoClient concertInfoClient;

    ArtistInfoServiceImpl(ConcertInfoClient concertInfoClient){
        this.concertInfoClient = concertInfoClient;
    }

    @Override
    public ArtistInfoDetailResponse getArtistById(String id) {
        List<Event> eventsForArtist = concertInfoClient.getEventsByArtistId(id);
        updateVenueForTheEvents(eventsForArtist);

        return ArtistInfoDetailResponse.builder()
                .artist(concertInfoClient.getArtistById(id))
                .events(eventsForArtist)
                .build();
    }

    private void updateVenueForTheEvents(List<Event> eventsForArtist) {
        eventsForArtist.forEach(event -> event.setVenue(
                concertInfoClient.getVenueById(event.getVenue().getId())));
    }
}
