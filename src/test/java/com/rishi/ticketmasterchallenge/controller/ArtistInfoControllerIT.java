package com.rishi.ticketmasterchallenge.controller;

import com.rishi.ticketmasterchallenge.service.ArtistInfoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArtistInfoControllerIT {

    @MockBean
    private ArtistInfoService artistInfoService;

    @Autowired
    private WebTestClient webTestClient;


    @Test
    void getReactiveArtistInfoByIdShouldReturnMono() {
//        when
       when(artistInfoService.getArtistDetailById(anyString())).thenReturn(new ArtistInfoDetailResponse());

//       then
        Mono<ArtistInfoDetailResponse> result = webTestClient.get()
                .uri("/artist/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(ArtistInfoDetailResponse.class)
                .getResponseBody().single();
    }

    @Test
    void getReactiveArtistInfoByIdShouldReturnMonoWithData() {
//        when
        when(artistInfoService.getArtistDetailById(anyString())).thenReturn(new ArtistInfoDetailResponse());

//       then
        this.webTestClient.get()
                .uri("/artist/1")
                .exchange()
                .expectBody()
                .jsonPath("$.size", Matchers.is(1));
    }

    @Test
    void getArtistInfoByIdShouldReturn500ErrorIfNoDataIsPresent() {
//        when
        when(artistInfoService.getArtistDetailById(anyString())).thenReturn(null);

//       then
        this.webTestClient.get()
                .uri("/artist/1")
                .exchange()
                .expectStatus().is5xxServerError();
    }
}