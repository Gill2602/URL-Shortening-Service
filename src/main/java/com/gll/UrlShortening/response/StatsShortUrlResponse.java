package com.gll.UrlShortening.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StatsShortUrlResponse {

    private Long id;

    private String originalUrl;
    private String shortenedCode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LocalDateTime lastAccessAt;
    private Integer numberOfAccess;
}
