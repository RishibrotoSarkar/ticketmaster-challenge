package com.rishi.ticketmasterchallenge.service;

import com.rishi.ticketmasterchallenge.controller.ArtistInfoDetailResponse;

public interface ArtistInfoService {
    ArtistInfoDetailResponse getArtistById(String id);
}
