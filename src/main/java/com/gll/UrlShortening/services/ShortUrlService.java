package com.gll.UrlShortening.services;

import com.gll.UrlShortening.entities.ShortUrlEntity;
import com.gll.UrlShortening.exceptions.AlreadyExistsException;
import com.gll.UrlShortening.exceptions.InvalidUrlException;
import com.gll.UrlShortening.exceptions.NotFoundException;
import com.gll.UrlShortening.mappers.Mapper;
import com.gll.UrlShortening.repositories.ShortUrlRepository;
import com.gll.UrlShortening.requests.ShortUrlRequest;
import com.gll.UrlShortening.responses.ShortUrlResponse;
import com.gll.UrlShortening.responses.ShortUrlStatsResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final ShortCodeGenerator shortCodeGenerator;

    private final Mapper<ShortUrlEntity, ShortUrlResponse> mapperToResponse;
    private final Mapper<ShortUrlEntity, ShortUrlStatsResponse> mapperToStatsResponse;

    private static final Logger log = LoggerFactory.getLogger(ShortUrlService.class);

    public ShortUrlResponse createShortUrl(ShortUrlRequest request) {

        if (shortUrlRepository.existsByUrl(request.getUrl())) {
            throw new AlreadyExistsException("Already exist a Shorted URL for: " + request.getUrl());
        }

        if (!URLValidator.isValidUrl(request.getUrl())) {
            throw new InvalidUrlException("The given URL is not properly formatted or contains invalid characters.");
        }

        ShortUrlEntity shortenedUrl = ShortUrlEntity.builder()
                .url(request.getUrl())
                .shortCode(shortCodeGenerator.next())
                .createdAt(LocalDateTime.now(ZoneId.of("Z")))
                .updatedAt(LocalDateTime.now(ZoneId.of("Z")))
                .lastAccessAt(LocalDateTime.now(ZoneId.of("Z")))
                .accessCount(0)
                .build();

        ShortUrlEntity saved = shortUrlRepository.save(shortenedUrl);
        log.info("created new short url with ID: {}",
                saved.getId()
        );

        return mapperToResponse.mapFrom(saved);
    }

    public ShortUrlResponse findByShortCode(String shortCode) {
        ShortUrlEntity result =  shortUrlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        result.setAccessCount(result.getAccessCount() + 1);
        result.setLastAccessAt(LocalDateTime.now(ZoneId.of("Z")));

        ShortUrlEntity saved = shortUrlRepository.save(result);
        log.info("retrieved a short url with ID: {}",
                saved.getId()
        );

        return mapperToResponse.mapFrom(saved);
    }

    public ShortUrlStatsResponse getShortUrlStats(String shortCode) {
        ShortUrlEntity result =  shortUrlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        ShortUrlEntity saved = shortUrlRepository.save(result);
        log.info("retrieved stats of short url with ID: {}",
                saved.getId()
        );

        return mapperToStatsResponse.mapFrom(saved);
    }

    public ShortUrlResponse updateShortUrl(String shortCode, ShortUrlRequest request) {

        if (!URLValidator.isValidUrl(request.getUrl())) {
            throw new InvalidUrlException("The given URL is not properly formatted or contains invalid characters.");
        }

        if (shortUrlRepository.existsByUrl(request.getUrl())) {
            throw new AlreadyExistsException("Already exist a Shorted URL for: " + request.getUrl());
        }

        ShortUrlEntity result = shortUrlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        result.setUrl(request.getUrl());
        result.setUpdatedAt(LocalDateTime.now(ZoneId.of("Z")));

        ShortUrlEntity saved = shortUrlRepository.save(result);
        log.info("deleted a short url with ID: {}",
                saved.getId()
        );

        return mapperToResponse.mapFrom(saved);
    }

    public void deleteShortUrl(String shortCode) {
        ShortUrlEntity result = shortUrlRepository.findByShortCode(shortCode)
                .orElseThrow( () -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        shortUrlRepository.delete(result);
    }
}
