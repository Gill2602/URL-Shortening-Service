package com.gll.UrlShortening.mappers;

import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import com.gll.UrlShortening.response.ShortUrlResponse;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public ShortUrlResponse mapFrom(ShortenedUrlEntity shortenedUrl){
        return ShortUrlResponse.builder()
                .id(shortenedUrl.getId())
                .originalUrl(shortenedUrl.getOriginalUrl())
                .shortedCode(shortenedUrl.getShortedCode())
                .createdAt(shortenedUrl.getCreatedAt())
                .updatedAt(shortenedUrl.getUpdatedAt())
                .build();
    }
}
