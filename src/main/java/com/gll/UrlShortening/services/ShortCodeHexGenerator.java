package com.gll.UrlShortening.services;

import com.gll.UrlShortening.repositories.ShortUrlRepository;
import org.springframework.stereotype.Service;

@Service
public class ShortCodeHexGenerator implements ShortCodeGenerator {

    private long counter = 0;
    private final ShortUrlRepository repository;

    public ShortCodeHexGenerator(final ShortUrlRepository repository) {
        this.repository = repository;

        counter = repository.findLastRecordCreated()
                .map(record -> Long.parseLong(record.getShortCode(), 16) + 1)
                .orElse(0L);
    }

    @Override
    public String next() {
        return Long.toHexString(counter++).toUpperCase();
    }
}
