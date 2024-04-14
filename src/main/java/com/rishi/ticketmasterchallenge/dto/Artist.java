package com.rishi.ticketmasterchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Artist {
    private String name;
    private String id;
    private String imgSrc;
    private String url;
    private Integer rank;
}
