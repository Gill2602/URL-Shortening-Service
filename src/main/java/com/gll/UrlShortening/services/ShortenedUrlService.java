package com.gll.UrlShortening.services;

import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import com.gll.UrlShortening.exceptions.AlreadyExistException;
import com.gll.UrlShortening.exceptions.NotFoundException;
import com.gll.UrlShortening.repositories.ShortenedUrlRepository;
import com.gll.UrlShortening.request.CreateShortUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class ShortenedUrlService {

    private final ShortenedUrlRepository shortenedUrlRepository;
    private final ShorterCodeGenerator shorterCodeGenerator;

    @Autowired
    public ShortenedUrlService(final ShortenedUrlRepository shortenedUrlRepository,
                               final ShorterCodeGenerator shorterCodeGenerator) {
        this.shortenedUrlRepository = shortenedUrlRepository;
        this.shorterCodeGenerator = shorterCodeGenerator;
    }

    public ShortenedUrlEntity createShortUrl(CreateShortUrlRequest request) {

        if (shortenedUrlRepository.existsByOriginalUrl(request.getNormalUrl())) {
            throw new AlreadyExistException("Already exist a Shorted URL for: " + request.getNormalUrl());
        }

        ShortenedUrlEntity shortenedUrl = ShortenedUrlEntity.builder()
                .originalUrl(request.getNormalUrl())
                .shortedCode(shorterCodeGenerator.next())
                .createdAt(ZonedDateTime.now(ZoneId.of("GMT")))
                .updatedAt(ZonedDateTime.now(ZoneId.of("GMT")))
                .lastAccessAt(ZonedDateTime.now(ZoneId.of("GMT")))
                .numberOfAccess(0)
                .build();

        return shortenedUrlRepository.save(shortenedUrl);
    }

    public ShortenedUrlEntity retrieveByShortUrl(String shortCode) {
        return shortenedUrlRepository.findByShortedCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));
    }
}
