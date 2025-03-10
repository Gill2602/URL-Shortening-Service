package com.gll.UrlShortening.controllers;

import com.gll.UrlShortening.entities.ShortUrlEntity;
import com.gll.UrlShortening.mappers.Mapper;
import com.gll.UrlShortening.request.ShortUrlRequest;
import com.gll.UrlShortening.response.ShortUrlResponse;
import com.gll.UrlShortening.response.ShortUrlStatsResponse;
import com.gll.UrlShortening.services.impl.ShortUrlServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/shorten")
public class UrlShorteningController {

    private final ShortUrlServiceImpl shortUrlService;
    private final Mapper<ShortUrlEntity, ShortUrlResponse> mapperToResponse;
    private final Mapper<ShortUrlEntity, ShortUrlStatsResponse> mapperToStatsResponse;

    public UrlShorteningController(final ShortUrlServiceImpl shortUrlService,
                                   final Mapper<ShortUrlEntity, ShortUrlResponse> mapperToResponse,
                                   final Mapper<ShortUrlEntity, ShortUrlStatsResponse> mapperToStatsResponse) {
        this.shortUrlService = shortUrlService;
        this.mapperToResponse = mapperToResponse;
        this.mapperToStatsResponse = mapperToStatsResponse;
    }

    @PostMapping
    public ResponseEntity<ShortUrlResponse> createShortUrl(@RequestBody ShortUrlRequest request) {
        ShortUrlEntity shortUrl = shortUrlService.createShortUrl(request);
        ShortUrlResponse response = mapperToResponse.mapFrom(shortUrl);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{shortCode}")
    public ResponseEntity<ShortUrlResponse> findOriginalUrl(@PathVariable("shortCode") String shortCode) {
        ShortUrlEntity shortUrl = shortUrlService.findByShortCode(shortCode);
        ShortUrlResponse response = mapperToResponse.mapFrom(shortUrl);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "/{shortCode}")
    public ResponseEntity<ShortUrlResponse> updateShortUrl(@PathVariable("shortCode") String shortCode,
                                                           @RequestBody ShortUrlRequest request) {
        ShortUrlEntity shortUrl = shortUrlService.updateShortUrl(shortCode, request);
        ShortUrlResponse response = mapperToResponse.mapFrom(shortUrl);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{shortCode}")
    public ResponseEntity<ShortUrlResponse> deleteShortUrl(@PathVariable("shortCode") String shortCode) {
        shortUrlService.deleteShortUrl(shortCode);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{shortCode}/stats")
    public ResponseEntity<ShortUrlStatsResponse> findShortUrlStats(@PathVariable("shortCode") String shortCode) {
        ShortUrlEntity shortUrl = shortUrlService.getShortUrlStats(shortCode);
        ShortUrlStatsResponse shortUrlResponse = mapperToStatsResponse.mapFrom(shortUrl);

        return new ResponseEntity<>(shortUrlResponse, HttpStatus.OK);
    }
}
