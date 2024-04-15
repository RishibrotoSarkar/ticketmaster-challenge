package com.rishi.ticketmasterchallenge.client;

import com.rishi.ticketmasterchallenge.dto.Artist;
import com.rishi.ticketmasterchallenge.dto.Event;
import com.rishi.ticketmasterchallenge.dto.Venue;
import com.rishi.ticketmasterchallenge.exception.DataNotAvailableException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class ConcertInfoClientImpl implements ConcertInfoClient{
    private final RestClient restClient;

    public ConcertInfoClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Artist getArtistById(String id) {
        return filterArtistById(restClient.get()
                        .uri("/artists.json").retrieve()
                        .body(new ParameterizedTypeReference<>(){}),
                id);
    }

    @Override
    public Venue getVenueById(String id) {
        return filterVenueById(restClient.get()
                .uri("/venues.json").retrieve()
                .body(new ParameterizedTypeReference<>() {}),
                id);
    }

    @Override
    public List<Event> getEventsByArtistId(String artistId) {

        return filterEventsByArtistId(restClient.get()
                .uri("/events.json").retrieve()
                .body(new ParameterizedTypeReference<>(){}),
                artistId);
    }

    private Artist filterArtistById(List<Artist> artists, String id) {
        return artists.stream().filter(artist -> artist.getId().equals(id)).findFirst()
                .orElseThrow(() -> new DataNotAvailableException("Artist not available for the id: " + id));
    }

    private Venue filterVenueById(List<Venue> venues, String id) {
        return venues.stream().filter(venue -> venue.getId().equals(id)).findFirst()
                .orElseThrow(() -> new DataNotAvailableException("No venue is available for the id" + id));
    }

    private List<Event> filterEventsByArtistId(List<Event> events, String id) {
        return events.stream().filter(event ->
                event.getArtists().stream().anyMatch(artist -> artist.getId().equals(id))).toList();
    }
}
