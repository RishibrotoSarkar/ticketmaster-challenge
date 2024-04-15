package com.rishi.ticketmasterchallenge.service;

import com.rishi.ticketmasterchallenge.client.ConcertInfoClient;
import com.rishi.ticketmasterchallenge.controller.ArtistInfoDetailResponse;
import com.rishi.ticketmasterchallenge.dto.Event;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ArtistInfoServiceImpl implements ArtistInfoService{

    private final ConcertInfoClient concertInfoClient;

    ArtistInfoServiceImpl(ConcertInfoClient concertInfoClient){
        this.concertInfoClient = concertInfoClient;
    }

    @Override
    public ArtistInfoDetailResponse getArtistDetailById(String id) {
        return ArtistInfoDetailResponse.builder()
                .artist(concertInfoClient.getArtistById(id))
                .events(getEventsForTheArtistWithId(id))
                .build();
    }

    private List<Event> getEventsForTheArtistWithId(String id) {
        List<Event> eventsForArtist = concertInfoClient.getEventsByArtistId(id);
        updateVenueForTheEvents(eventsForArtist);

        return eventsForArtist;
    }

    private void updateVenueForTheEvents(List<Event> eventsForArtist) {
        if (!isNull(eventsForArtist))
            eventsForArtist.forEach(event -> {
                event.setArtists(null);
                event.setVenue(
                        concertInfoClient.getVenueById(event.getVenue().getId()));
            });
    }
}
