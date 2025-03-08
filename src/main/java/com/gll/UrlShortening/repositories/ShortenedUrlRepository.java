package com.gll.UrlShortening.repositories;

import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import org.springframework.data.repository.CrudRepository;

public interface ShortenedUrlRepository extends CrudRepository<ShortenedUrlEntity, Long> {

    boolean existsByOriginalUrl(String originalUrl);
}
