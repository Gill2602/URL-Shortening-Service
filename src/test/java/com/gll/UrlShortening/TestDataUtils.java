package com.gll.UrlShortening;

import com.gll.UrlShortening.entities.ShortUrlEntity;
import com.gll.UrlShortening.request.ShortUrlRequest;

import java.time.LocalDateTime;
import java.util.List;

public final class TestDataUtils {

    public static List<ShortUrlEntity> getSomeShortenedUrl(){
        return List.of(
                ShortUrlEntity.builder()
                        .url("https://www.youtube.com/")
                        .shortCode("1A")
                        .createdAt(LocalDateTime.of(2024, 3, 9, 6, 0))
                        .updatedAt(LocalDateTime.of(2025, 2, 26, 12, 0))
                        .lastAccessAt(LocalDateTime.of(2025, 2, 27, 17, 30))
                        .accessCount(173)
                        .build(),

                ShortUrlEntity.builder()
                        .url("https://www.google.com/")
                        .shortCode("2B")
                        .createdAt(LocalDateTime.of(2024, 3, 10, 7, 30))
                        .updatedAt(LocalDateTime.of(2025, 2, 20, 10, 15))
                        .lastAccessAt(LocalDateTime.of(2025, 2, 28, 9, 45))
                        .accessCount(289)
                        .build(),

                ShortUrlEntity.builder()
                        .url("https://www.facebook.com/")
                        .shortCode("3C")
                        .createdAt(LocalDateTime.of(2024, 4, 5, 14, 0))
                        .updatedAt(LocalDateTime.of(2025, 1, 15, 16, 30))
                        .lastAccessAt(LocalDateTime.of(2025, 2, 25, 22, 0))
                        .accessCount(512)
                        .build(),

                ShortUrlEntity.builder()
                        .url("https://www.twitter.com/")
                        .shortCode("4D")
                        .createdAt(LocalDateTime.of(2024, 2, 1, 9, 0))
                        .updatedAt(LocalDateTime.of(2025, 2, 10, 14, 0))
                        .lastAccessAt(LocalDateTime.of(2025, 2, 20, 18, 15))
                        .accessCount(634)
                        .build(),

                ShortUrlEntity.builder()
                        .url("https://www.medium.com/")
                        .shortCode("AD")
                        .createdAt(LocalDateTime.of(2024, 9, 10, 11, 0))
                        .updatedAt(LocalDateTime.of(2025, 2, 15, 8, 30))
                        .lastAccessAt(LocalDateTime.of(2025, 2, 27, 18, 0))
                        .accessCount(245)
                        .build()
        );

    }

    public static List<ShortUrlRequest> getSomeValidRequest(){
        return List.of(
                new ShortUrlRequest("https://mail.google.com/mail"),
                new ShortUrlRequest("https://docs.google.com/document"),
                new ShortUrlRequest("https://www.linux.it/")
        );
    }

    public static List<ShortUrlRequest> getSomeInvalidRequest(){
        return List.of(
                new ShortUrlRequest("htt:/mail.google.com/mail"),
                new ShortUrlRequest("htps//docs.google.com"),
                new ShortUrlRequest("linux.it")
        );
    }
}
