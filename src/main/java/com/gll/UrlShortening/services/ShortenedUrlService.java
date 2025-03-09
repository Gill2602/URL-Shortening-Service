package com.gll.UrlShortening.services;

import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import com.gll.UrlShortening.exceptions.AlreadyExistException;
import com.gll.UrlShortening.exceptions.NotFoundException;
import com.gll.UrlShortening.repositories.ShortenedUrlRepository;
import com.gll.UrlShortening.request.ShortUrlRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ShortenedUrlService {

    private final ShortenedUrlRepository shortenedUrlRepository;
    private final ShorterCodeGenerator shorterCodeGenerator;

    public ShortenedUrlService(final ShortenedUrlRepository shortenedUrlRepository,
                               final ShorterCodeGenerator shorterCodeGenerator) {
        this.shortenedUrlRepository = shortenedUrlRepository;
        this.shorterCodeGenerator = shorterCodeGenerator;
    }

    public ShortenedUrlEntity createShortUrl(ShortUrlRequest request) {

        if (shortenedUrlRepository.existsByOriginalUrl(request.getUrl())) {
            throw new AlreadyExistException("Already exist a Shorted URL for: " + request.getUrl());
        }

        ShortenedUrlEntity shortenedUrl = ShortenedUrlEntity.builder()
                .originalUrl(request.getUrl())
                .shortenedCode(shorterCodeGenerator.next())
                .createdAt(LocalDateTime.now(ZoneId.of("UTC")))
                .updatedAt(LocalDateTime.now(ZoneId.of("UTC")))
                .lastAccessAt(LocalDateTime.now(ZoneId.of("UTC")))
                .numberOfAccess(0)
                .build();

        return shortenedUrlRepository.save(shortenedUrl);
    }

    public ShortenedUrlEntity retrieveByShortUrl(String shortCode) {
        ShortenedUrlEntity result =  shortenedUrlRepository.findByShortenedCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        result.setNumberOfAccess(result.getNumberOfAccess() + 1);
        result.setLastAccessAt(LocalDateTime.now(ZoneId.of("UTC")));

        return shortenedUrlRepository.save(result);
    }

    public ShortenedUrlEntity getStatsByShortUrl(String shortCode) {
        ShortenedUrlEntity result =  shortenedUrlRepository.findByShortenedCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        return shortenedUrlRepository.save(result);
    }

    public ShortenedUrlEntity updateShortUrl(String shortCode, ShortUrlRequest request) {

        ShortenedUrlEntity result = shortenedUrlRepository.findByShortenedCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        if (shortenedUrlRepository.existsByOriginalUrl(request.getUrl())) {
            throw new AlreadyExistException("Already exist a Shorted URL for: " + request.getUrl());
        }

        result.setOriginalUrl(request.getUrl());
        result.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        return shortenedUrlRepository.save(result);
    }

    public void deleteShortUrl(String shortCode) {
        ShortenedUrlEntity result = shortenedUrlRepository.findByShortenedCode(shortCode)
                .orElseThrow( () -> new NotFoundException("Don't exist a record with shorted code: " + shortCode));

        shortenedUrlRepository.delete(result);
    }
}
