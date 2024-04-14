package com.rishi.ticketmasterchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venue {
    private String name;
    private String url;
    private String city;
    private String id;
}
