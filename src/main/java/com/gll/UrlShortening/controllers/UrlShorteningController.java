package com.gll.UrlShortening.controllers;

import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import com.gll.UrlShortening.mappers.Mapper;
import com.gll.UrlShortening.request.ShortUrlRequest;
import com.gll.UrlShortening.response.ShortUrlResponse;
import com.gll.UrlShortening.response.StatsShortUrlResponse;
import com.gll.UrlShortening.services.ShortenedUrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/shorten")
public class UrlShorteningController {

    private final ShortenedUrlService shortenedUrlService;
    private final Mapper mapper;

    public UrlShorteningController(final ShortenedUrlService shortenedUrlService,
                                   final Mapper mapper) {
        this.shortenedUrlService = shortenedUrlService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ShortUrlResponse> createShortUrl(@RequestBody ShortUrlRequest request) {
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

    @PutMapping(path = "/{shortCode}")
    public ResponseEntity<ShortUrlResponse> updateShortUrl(@PathVariable("shortCode") String shortCode,
                                                           @RequestBody ShortUrlRequest request) {
        ShortenedUrlEntity shortenedUrl = shortenedUrlService.updateShortUrl(shortCode, request);
        ShortUrlResponse response = mapper.mapFrom(shortenedUrl);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{shortCode}")
    public ResponseEntity<ShortUrlResponse> deleteShortUrl(@PathVariable("shortCode") String shortCode) {
        shortenedUrlService.deleteShortUrl(shortCode);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{shortCode}/stats")
    public ResponseEntity<StatsShortUrlResponse> GetUrlStatistics(@PathVariable("shortCode") String shortCode) {
        ShortenedUrlEntity shortenedUrl = shortenedUrlService.getStatsByShortUrl(shortCode);
        StatsShortUrlResponse shortUrlResponse = mapper.StatsMapFrom(shortenedUrl);

        return new ResponseEntity<>(shortUrlResponse, HttpStatus.OK);
    }
}
