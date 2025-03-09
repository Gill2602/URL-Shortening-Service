package com.gll.UrlShortening.repositories;

import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortenedUrlRepository extends CrudRepository<ShortenedUrlEntity, Long> {

    Optional<ShortenedUrlEntity> findTopByOrderByCreatedAtDesc();

    Optional<ShortenedUrlEntity> findByShortenedCode(String shortedCode);

    boolean existsByOriginalUrl(String originalUrl);

}
