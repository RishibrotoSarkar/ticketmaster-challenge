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
                        .body(new ParameterizedTypeReference<>() {})
                , id);
    }

    @Override
    public Venue getVenueById(String id) {
        return null;
    }

    @Override
    public List<Event> getEventsByArtistId(String artistId) {
        return null;
    }

    private Artist filterArtistById(List<Artist> artists, String id) {
        return artists.stream().filter(artist -> artist.getId().equals(id)).findFirst()
                .orElseThrow(() -> new DataNotAvailableException("Artists not available for the id: " + id));
    }
}
