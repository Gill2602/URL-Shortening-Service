package com.gll.UrlShortening.services.impl;

import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import com.gll.UrlShortening.exceptions.AlreadyExistException;
import com.gll.UrlShortening.exceptions.InvalidUrlException;
import com.gll.UrlShortening.exceptions.NotFoundException;
import com.gll.UrlShortening.repositories.ShortenedUrlRepository;
import com.gll.UrlShortening.request.ShortUrlRequest;
import com.gll.UrlShortening.services.ShortenedUrlService;
import com.gll.UrlShortening.services.ShorterCodeGenerator;
import com.gll.UrlShortening.services.URLValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ShortenedUrlServiceImpl implements ShortenedUrlService {

    private final ShortenedUrlRepository shortenedUrlRepository;
    private final ShorterCodeGenerator shorterCodeGenerator;
    private final URLValidator urlValidator;

    public ShortenedUrlServiceImpl(final ShortenedUrlRepository shortenedUrlRepository,
                                   final ShorterCodeGenerator shorterCodeGenerator,
                                   final URLValidator urlValidator) {
        this.shortenedUrlRepository = shortenedUrlRepository;
        this.shorterCodeGenerator = shorterCodeGenerator;
        this.urlValidator = urlValidator;
    }

    @Override
    public ShortenedUrlEntity createShortUrl(ShortUrlRequest request) {

        if (shortenedUrlRepository.existsByOriginalUrl(request.getUrl())) {
            throw new AlreadyExistException("Already exist a Shorted URL for: " + request.getUrl());
        }

        if (!urlValidator.isValidUrl(request.getUrl())) {
            throw new InvalidUrlException("The given URL is not properly formatted or contains invalid characters.");
        }

        ShortenedUrlEntity shortenedUrl = ShortenedUrlEntity.builder()
                .originalUrl(request.getUrl())
                .shortenedCode(shorterCodeGenerator.next())
                .createdAt(LocalDateTime.now(ZoneId.of("Z")))
                .updatedAt(LocalDateTime.now(ZoneId.of("Z")))
                .lastAccessAt(LocalDateTime.now(ZoneId.of("Z")))
                .numberOfAccess(0)
                .build();

        return shortenedUrlRepository.save(shortenedUrl);
    }

    @Override
    public ShortenedUrlEntity retrieveByShortUrl(String shortCode) {
        ShortenedUrlEntity result =  shortenedUrlRepository.findByShortenedCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        result.setNumberOfAccess(result.getNumberOfAccess() + 1);
        result.setLastAccessAt(LocalDateTime.now(ZoneId.of("Z")));

        return shortenedUrlRepository.save(result);
    }

    @Override
    public ShortenedUrlEntity getStatsByShortUrl(String shortCode) {
        ShortenedUrlEntity result =  shortenedUrlRepository.findByShortenedCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        return shortenedUrlRepository.save(result);
    }

    @Override
    public ShortenedUrlEntity updateShortUrl(String shortCode, ShortUrlRequest request) {

        if (!urlValidator.isValidUrl(request.getUrl())) {
            throw new InvalidUrlException("The given URL is not properly formatted or contains invalid characters.");
        }

        if (shortenedUrlRepository.existsByOriginalUrl(request.getUrl())) {
            throw new AlreadyExistException("Already exist a Shorted URL for: " + request.getUrl());
        }

        ShortenedUrlEntity result = shortenedUrlRepository.findByShortenedCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        result.setOriginalUrl(request.getUrl());
        result.setUpdatedAt(LocalDateTime.now(ZoneId.of("Z")));

        return shortenedUrlRepository.save(result);
    }

    @Override
    public void deleteShortUrl(String shortCode) {
        ShortenedUrlEntity result = shortenedUrlRepository.findByShortenedCode(shortCode)
                .orElseThrow( () -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        shortenedUrlRepository.delete(result);
    }
}
