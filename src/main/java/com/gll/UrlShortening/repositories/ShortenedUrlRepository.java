package com.gll.UrlShortening.repositories;

import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortenedUrlRepository extends CrudRepository<ShortenedUrlEntity, Long> {

    @Query("SELECT s FROM ShortenedURL s ORDER BY s.createdAt DESC LIMIT 1")
    Optional<ShortenedUrlEntity> findLastRecordCreated();

    Optional<ShortenedUrlEntity> findByShortenedCode(String shortedCode);

    boolean existsByOriginalUrl(String originalUrl);

}
