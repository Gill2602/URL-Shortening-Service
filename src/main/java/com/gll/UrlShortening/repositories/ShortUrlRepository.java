package com.gll.UrlShortening.repositories;

import com.gll.UrlShortening.entities.ShortUrlEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends CrudRepository<ShortUrlEntity, Long> {

    @Query("SELECT s FROM ShortURL s ORDER BY s.createdAt DESC LIMIT 1")
    Optional<ShortUrlEntity> findLastRecordCreated();

    Optional<ShortUrlEntity> findByShortCode(String shortedCode);

    boolean existsByUrl(String url);

}
