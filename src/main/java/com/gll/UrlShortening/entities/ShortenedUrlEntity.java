package com.gll.UrlShortening.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ShortenedURLs")
@Entity(name = "ShortenedURL")
public class ShortenedUrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_url", length = 4096, unique = true, nullable = false)
    private String originalUrl;
    @Column(name = "shortened_code", length = 255, unique = true, nullable = false)
    private String shortenedCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @Column(name = "last_access_at", nullable = false)
    private LocalDateTime lastAccessAt;

    @Column(name = "number_of_access", nullable = false)
    private Integer numberOfAccess;
}
