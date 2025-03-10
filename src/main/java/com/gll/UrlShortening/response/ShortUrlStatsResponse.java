package com.gll.UrlShortening.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
