package com.gll.UrlShortening.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ShortUrlStatsResponse {

    private Long id;

    private String url;
    private String shortCode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LocalDateTime lastAccessAt;
    private Integer accessCount;
}
