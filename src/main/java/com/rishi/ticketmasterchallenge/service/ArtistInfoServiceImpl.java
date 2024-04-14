package com.rishi.ticketmasterchallenge.service;

import com.rishi.ticketmasterchallenge.client.ConcertInfoClient;
import com.rishi.ticketmasterchallenge.dto.Artist;
import org.springframework.stereotype.Service;

@Service
public class ArtistInfoServiceImpl implements ArtistInfoService{

    private final ConcertInfoClient concertInfoClient;

    ArtistInfoServiceImpl(ConcertInfoClient concertInfoClient){
        this.concertInfoClient = concertInfoClient;
    }

    @Override
    public Artist getArtistById(String id) {
        return concertInfoClient.getArtistById(id);
    }
}
