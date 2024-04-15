package com.rishi.ticketmasterchallenge.service;

import com.rishi.ticketmasterchallenge.client.ConcertInfoClient;
import com.rishi.ticketmasterchallenge.controller.ArtistInfoDetailResponse;
import com.rishi.ticketmasterchallenge.dto.Event;
import com.rishi.ticketmasterchallenge.dto.Venue;
import com.rishi.ticketmasterchallenge.exception.DataNotAvailableException;
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
        if (!isNull(eventsForArtist)) {
            final List<Venue> allVenues = concertInfoClient.getVenues();
            eventsForArtist.forEach(event -> {
                String venueId = event.getVenue().getId();
                event.setArtists(null);
                event.setVenue(filterVenueById(allVenues, venueId));
            });
        }
    }

    private Venue filterVenueById(List<Venue> venues, String id) {
        return venues.stream().filter(venue -> venue.getId().equals(id)).findFirst()
                .orElseThrow(() -> new DataNotAvailableException("No venue is available for the id" + id));
    }
}
