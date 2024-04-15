package com.rishi.ticketmasterchallenge.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rishi.ticketmasterchallenge.dto.Artist;
import com.rishi.ticketmasterchallenge.dto.Event;
import com.rishi.ticketmasterchallenge.dto.Venue;
import com.rishi.ticketmasterchallenge.exception.DataNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertInfoClientImplTest {

    @Mock
    RestClient client;
    @Mock
    RestClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    RestClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    RestClient.ResponseSpec responseSpec;

    private ConcertInfoClient underTest;

    @BeforeEach
    void setUp() {
        underTest = new ConcertInfoClientImpl(client);

        when(client.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void getArtistByIdShouldReturnTheArtistWhenArtistIsPresentInTheList() throws JsonProcessingException {
        List<Artist> dummyListOfArtists = List.of(
                Artist.builder().id("1").name("artist1").build(),
                Artist.builder().id("2").name("artist2").build());

//        when
        when(client.get().uri(anyString()).retrieve().body(any(ParameterizedTypeReference.class)))
                .thenReturn(dummyListOfArtists);

//        then
        Artist result = underTest.getArtistById("1");
        assertThat(result.getId()).isEqualTo("1");
        assertThat(result.getName()).isEqualTo("artist1");
    }

    @Test
    void getArtistByIdShouldThrowHttpClientErrorExceptionWhenWrongUriIsConfigured() {

        //        when
        when(client.get().uri(anyString()).retrieve().body(any(ParameterizedTypeReference.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN));

        //  then
        assertThrows(HttpClientErrorException.class, () -> underTest.getArtistById("1"));
    }

    @Test
    void getArtistByIdShouldThrowDataNotAvailableExceptionWhenNoArtistIsPresentForTheId() {

        //        when
        when(client.get().uri(anyString()).retrieve().body(any(ParameterizedTypeReference.class)))
                .thenThrow(new DataNotAvailableException("Artists not available for the id"));

        //  then
        assertThrows(DataNotAvailableException.class, () -> underTest.getArtistById("1"));
    }

    @Test
    void getVenueByIdShouldReturnTheVenueDetailsWhenTheVenueInfoIsPresent() {
        List<Venue> dummyListOfVenues = List.of(
                Venue.builder().id("vId1").name("vName1").build(),
                Venue.builder().id("vId2").name("vName2").build()
        );

//        when
        when(client.get().uri(anyString()).retrieve().body(any(ParameterizedTypeReference.class)))
                .thenReturn(dummyListOfVenues);

//        then
        Venue result = underTest.getVenueById("vId1");
        assertThat(result.getId()).isEqualTo("vId1");
        assertThat(result.getName()).isEqualTo("vName1");
    }

    @Test
    void getEventsByArtistIdShouldReturnTheListOfEventsWhenTheArtistIsPerformingInMultipleEvents() {
        List<Artist> artistsForEvent1 = List.of(
                Artist.builder().id("a1").build(),
                Artist.builder().id("a2").build()
        );

        List<Artist> artistsForEvent2 = List.of(
                Artist.builder().id("a2").build(),
                Artist.builder().id("a3").build()
        );

        List<Artist> artistsForEvent3 = List.of(
                Artist.builder().id("a1").build(),
                Artist.builder().id("a3").build()
        );

        List<Event> dummyListOfEvents = List.of(
                Event.builder().id("e1").artists(artistsForEvent1).build(),
                Event.builder().id("e2").artists(artistsForEvent2).build(),
                Event.builder().id("e3").artists(artistsForEvent3).build()
        );

//        when
        when(client.get().uri(anyString()).retrieve().body(any(ParameterizedTypeReference.class)))
                .thenReturn(dummyListOfEvents);

//        then
        List<Event> result = underTest.getEventsByArtistId("a1");
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo("e1");
        assertThat(result.get(1).getId()).isEqualTo("e3");

    }
}