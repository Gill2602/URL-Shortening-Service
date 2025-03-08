package com.gll.UrlShortening.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortenedUrlDTO {

    private Long id;

    private String originalUrl;
    private String shortedUrl;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime lastAccessAt;

    private Integer numberOfAccess;
}
