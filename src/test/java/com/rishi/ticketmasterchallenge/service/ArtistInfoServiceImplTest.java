package com.rishi.ticketmasterchallenge.service;

import com.rishi.ticketmasterchallenge.client.ConcertInfoClient;
import com.rishi.ticketmasterchallenge.controller.ArtistInfoDetailResponse;
import com.rishi.ticketmasterchallenge.dto.Artist;
import com.rishi.ticketmasterchallenge.dto.Event;
import com.rishi.ticketmasterchallenge.dto.Venue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistInfoServiceImplTest {
    @Mock
    private ConcertInfoClient client;

    private ArtistInfoService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ArtistInfoServiceImpl(client);
    }

    @Test
    void shouldexecuteTheVenueApuAsManyTimesEventsArePresent() {
        Artist artist = createArtist();

//        when
        when(client.getArtistById(any())).thenReturn(artist);
        when(client.getEventsByArtistId(any())).thenReturn(createEvents());
        when(client.getVenueById(any())).thenReturn(createVenueWithIdAndName("vId1", "vName1"));

//        then
        ArtistInfoDetailResponse result = underTest.getArtistDetailById("id");
        int eventCount = result.getEvents().size();

        verify(client, times(1)).getArtistById(any());
        verify(client, times(1)).getEventsByArtistId(any());
        verify(client, times(eventCount)).getVenueById(any());
    }

    @Test
    void shouldReturnArtistInfoDetailWhenTheArtistExistForTheGivenId() {
        Artist artist = createArtist();

//        when
        when(client.getArtistById(any())).thenReturn(artist);
        when(client.getEventsByArtistId(any())).thenReturn(createEvents());
        when(client.getVenueById(any())).thenReturn(createVenueWithIdAndName("vId1", "vName1"));

//        then
        ArtistInfoDetailResponse result = underTest.getArtistDetailById("id");
        assertNotNull(result.getArtist());
        assertThat(result.getArtist().getId()).isEqualTo("id");
        assertThat(result.getArtist().getName()).isEqualTo("artistName");

        assertNotNull(result.getEvents());
        assertThat(result.getEvents().size()).isEqualTo(2);
        assertThat(result.getEvents().get(0).getId()).isEqualTo("1");

        assertNotNull(result.getEvents().get(0).getVenue());
        assertThat(result.getEvents().get(0).getVenue().getId()).isEqualTo("vId1");
        assertThat(result.getEvents().get(0).getVenue().getName()).isEqualTo("vName1");

        assertThat(result.getEvents().get(1).getId()).isEqualTo("2");
        assertThat(result.getEvents().get(1).getTitle()).isEqualTo("title2");

        assertNotNull(result.getEvents().get(1).getVenue());
        assertThat(result.getEvents().get(1).getVenue().getId()).isEqualTo("vId1");
        assertThat(result.getEvents().get(1).getVenue().getName()).isEqualTo("vName1");

    }

    private Artist createArtist() {
        return Artist.builder()
                .id("id")
                .name("artistName")
                .build();
    }

    private List<Event> createEvents() {
        return List.of(
                Event.builder().id("1").title("title1")
                        .venue(createVenueWithId("vId1"))
                        .build(),
                Event.builder().id("2").title("title2")
                        .venue(createVenueWithId("vId2"))
                        .build()
        );
    }

    private Venue createVenueWithId(String id) {
        return Venue.builder()
                .id(id)
                .build();
    }

    private Venue createVenueWithIdAndName(String id, String name) {
        return Venue.builder()
                .id(id)
                .name(name)
                .build();
    }
}