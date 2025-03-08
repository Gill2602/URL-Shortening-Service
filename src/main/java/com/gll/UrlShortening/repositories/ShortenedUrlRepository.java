package com.gll.UrlShortening.repositories;

import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShortenedUrlRepository extends CrudRepository<ShortenedUrlEntity, Long> {

    Optional<ShortenedUrlEntity> findTopByOrderByCreatedAtDesc();

    Optional<ShortenedUrlEntity> findByShortedCode(String shortedCode);

    boolean existsByOriginalUrl(String originalUrl);

}
