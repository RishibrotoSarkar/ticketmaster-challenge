package com.rishi.ticketmasterchallenge.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rishi.ticketmasterchallenge.dto.Artist;
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
    void whenArtistIsPresentInThenListThenGetArtistByIdWillReturnTheArtist() throws JsonProcessingException {
        String uri = "https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/artists.json";
        List<Artist> dummyListOfArtists = List.of(
                Artist.builder().id("1").name("artist1").build(),
                Artist.builder().id("2").name("artist2").build());

//        when
        when(client.get().uri(uri).retrieve().body(any(ParameterizedTypeReference.class)))
                .thenReturn(dummyListOfArtists);

//        then
        Artist result = underTest.getArtistById("1");
        assertThat(result.getId()).isEqualTo("1");
        assertThat(result.getName()).isEqualTo("artist1");
    }

    @Test
    void whenWrongUriIsConfiguredThenGetArtistByIdWillThrowHttpClientErrorException() {
        String uri = "https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/artists.json";

        //        when
        when(client.get().uri(uri).retrieve().body(any(ParameterizedTypeReference.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN));

        //  then
        assertThrows(HttpClientErrorException.class, () -> underTest.getArtistById("1"));
    }

    @Test
    void whenNoArtistIsPresentForTheIdThenGetArtistByIdWillThrowDataNotAvailableException() {
        String uri = "https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/artists.json";

        //        when
        when(client.get().uri(uri).retrieve().body(any(ParameterizedTypeReference.class)))
                .thenThrow(new DataNotAvailableException("Artists not available for the id"));

        //  then
        assertThrows(DataNotAvailableException.class, () -> underTest.getArtistById("1"));
    }

    @Test
    void getVenueById() {
    }

    @Test
    void getEventsByArtistId() {
    }
}