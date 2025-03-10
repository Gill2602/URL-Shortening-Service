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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ShortURLs")
@Entity(name = "ShortURL")
public class ShortUrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", length = 4096, unique = true, nullable = false)
    private String url;
    @Column(name = "short_code", length = 255, unique = true, nullable = false)
    private String shortCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @Column(name = "last_access_at", nullable = false)
    private LocalDateTime lastAccessAt;

    @Column(name = "access_count", nullable = false)
    private Integer accessCount;
}
