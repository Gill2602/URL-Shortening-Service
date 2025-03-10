package com.gll.UrlShortening.services.impl;

import com.gll.UrlShortening.entities.ShortUrlEntity;
import com.gll.UrlShortening.exceptions.AlreadyExistsException;
import com.gll.UrlShortening.exceptions.InvalidUrlException;
import com.gll.UrlShortening.exceptions.NotFoundException;
import com.gll.UrlShortening.repositories.ShortUrlRepository;
import com.gll.UrlShortening.request.ShortUrlRequest;
import com.gll.UrlShortening.services.ShortUrlService;
import com.gll.UrlShortening.services.ShortCodeGenerator;
import com.gll.UrlShortening.services.UrlValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final ShortCodeGenerator shortCodeGenerator;
    private final UrlValidator urlValidator;

    public ShortUrlServiceImpl(final ShortUrlRepository shortUrlRepository,
                               final ShortCodeGenerator shortCodeGenerator,
                               final UrlValidator urlValidator) {
        this.shortUrlRepository = shortUrlRepository;
        this.shortCodeGenerator = shortCodeGenerator;
        this.urlValidator = urlValidator;
    }

    @Override
    public ShortUrlEntity createShortUrl(ShortUrlRequest request) {

        if (shortUrlRepository.existsByUrl(request.getUrl())) {
            throw new AlreadyExistsException("Already exist a Shorted URL for: " + request.getUrl());
        }

        if (!urlValidator.isValidUrl(request.getUrl())) {
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

        return shortUrlRepository.save(shortenedUrl);
    }

    @Override
    public ShortUrlEntity findByShortCode(String shortCode) {
        ShortUrlEntity result =  shortUrlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        result.setAccessCount(result.getAccessCount() + 1);
        result.setLastAccessAt(LocalDateTime.now(ZoneId.of("Z")));

        return shortUrlRepository.save(result);
    }

    @Override
    public ShortUrlEntity getShortUrlStats(String shortCode) {
        ShortUrlEntity result =  shortUrlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        return shortUrlRepository.save(result);
    }

    @Override
    public ShortUrlEntity updateShortUrl(String shortCode, ShortUrlRequest request) {

        if (!urlValidator.isValidUrl(request.getUrl())) {
            throw new InvalidUrlException("The given URL is not properly formatted or contains invalid characters.");
        }

        if (shortUrlRepository.existsByUrl(request.getUrl())) {
            throw new AlreadyExistsException("Already exist a Shorted URL for: " + request.getUrl());
        }

        ShortUrlEntity result = shortUrlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        result.setUrl(request.getUrl());
        result.setUpdatedAt(LocalDateTime.now(ZoneId.of("Z")));

        return shortUrlRepository.save(result);
    }

    @Override
    public void deleteShortUrl(String shortCode) {
        ShortUrlEntity result = shortUrlRepository.findByShortCode(shortCode)
                .orElseThrow( () -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        shortUrlRepository.delete(result);
    }
}
