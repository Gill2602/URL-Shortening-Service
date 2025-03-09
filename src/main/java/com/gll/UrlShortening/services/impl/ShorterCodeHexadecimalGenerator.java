package com.gll.UrlShortening.services.impl;

import com.gll.UrlShortening.repositories.ShortenedUrlRepository;
import com.gll.UrlShortening.services.ShorterCodeGenerator;
import org.springframework.stereotype.Service;

@Service
public class ShorterCodeHexadecimalGenerator implements ShorterCodeGenerator {

    private long counter = 0;
    private final ShortenedUrlRepository repository;

    public ShorterCodeHexadecimalGenerator(final ShortenedUrlRepository repository) {
        this.repository = repository;

        counter = repository.findTopByOrderByCreatedAtDesc()
                .map(record -> Long.parseLong(record.getShortenedCode(), 16) + 1)
                .orElse(0L);
    }

    public String next() {
        return Long.toHexString(counter++).toUpperCase();
    }
}
