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
    @Column(name = "shorted_code", length = 128, unique = true, nullable = false)
    private String shortedCode;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;
    @Column(name = "last_access_at", nullable = false)
    private ZonedDateTime lastAccessAt;

    @Column(name = "number_of_access", nullable = false)
    private Integer numberOfAccess;
}
