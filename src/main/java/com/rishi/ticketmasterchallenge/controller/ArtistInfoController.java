package com.rishi.ticketmasterchallenge.controller;

import com.rishi.ticketmasterchallenge.service.ArtistInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ArtistInfoController {

    private final ArtistInfoService artistInfoService;
    ArtistInfoController(ArtistInfoService artistInfoService) {
        this.artistInfoService = artistInfoService;
    }

    @GetMapping("/artist/{id}")
    public Mono<ArtistInfoDetailResponse> getReactiveArtistInfoById(@PathVariable String id) {
        return Mono.just(artistInfoService.getArtistDetailById(id));
    }

}
