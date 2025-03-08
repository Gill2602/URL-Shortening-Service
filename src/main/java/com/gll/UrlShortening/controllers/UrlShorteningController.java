package com.gll.UrlShortening.controllers;

import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import com.gll.UrlShortening.mappers.Mapper;
import com.gll.UrlShortening.request.CreateShortUrlRequest;
import com.gll.UrlShortening.response.ShortUrlResponse;
import com.gll.UrlShortening.services.ShortenedUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/shorten")
public class UrlShorteningController {

    private final ShortenedUrlService shortenedUrlService;
    private final Mapper mapper;

    @Autowired
    public UrlShorteningController(final ShortenedUrlService shortenedUrlService,
                                   final Mapper mapper) {
        this.shortenedUrlService = shortenedUrlService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ShortUrlResponse> createShortUrl(@RequestBody CreateShortUrlRequest request) {
        ShortenedUrlEntity shortenedUrl = shortenedUrlService.createShortUrl(request);
        ShortUrlResponse response = mapper.mapFrom(shortenedUrl);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{shortCode}")
    public ResponseEntity<ShortUrlResponse> retrieveOriginalUrl(@PathVariable("shortCode") String shortCode) {
        ShortenedUrlEntity shortenedUrl = shortenedUrlService.retrieveByShortUrl(shortCode);
        ShortUrlResponse response = mapper.mapFrom(shortenedUrl);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
