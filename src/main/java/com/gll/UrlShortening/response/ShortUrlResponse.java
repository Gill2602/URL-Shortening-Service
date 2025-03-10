package com.gll.UrlShortening.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Builder
public class ShortUrlResponse {

    private Long id;

    private String originalUrl;
    private String shortenedCode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
