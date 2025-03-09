package com.gll.UrlShortening.mappers;

import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import com.gll.UrlShortening.response.ShortUrlResponse;
import com.gll.UrlShortening.response.StatsShortUrlResponse;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public ShortUrlResponse mapFrom(ShortenedUrlEntity shortenedUrl){
        return ShortUrlResponse.builder()
                .id(shortenedUrl.getId())
                .originalUrl(shortenedUrl.getOriginalUrl())
                .shortenedCode(shortenedUrl.getShortenedCode())
                .createdAt(shortenedUrl.getCreatedAt())
                .updatedAt(shortenedUrl.getUpdatedAt())
                .build();
    }

    public StatsShortUrlResponse StatsMapFrom(ShortenedUrlEntity shortenedUrl){
        return StatsShortUrlResponse.builder()
                .id(shortenedUrl.getId())
                .originalUrl(shortenedUrl.getOriginalUrl())
                .shortenedCode(shortenedUrl.getShortenedCode())
                .createdAt(shortenedUrl.getCreatedAt())
                .updatedAt(shortenedUrl.getUpdatedAt())
                .lastAccessAt(shortenedUrl.getLastAccessAt())
                .numberOfAccess(shortenedUrl.getNumberOfAccess())
                .build();
    }
}
