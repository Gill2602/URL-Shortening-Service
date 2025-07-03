package com.gll.UrlShortening.controllers;

import com.gll.UrlShortening.requests.ShortUrlRequest;
import com.gll.UrlShortening.responses.ShortUrlResponse;
import com.gll.UrlShortening.responses.ShortUrlStatsResponse;
import com.gll.UrlShortening.services.ShortUrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/shorten")
public class UrlShorteningController {

    private final ShortUrlService shortUrlService;

    public UrlShorteningController(final ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping
    public ResponseEntity<ShortUrlResponse> createShortUrl(@RequestBody ShortUrlRequest request) {
        return new ResponseEntity<>(shortUrlService.createShortUrl(request), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{shortCode}")
    public ResponseEntity<ShortUrlResponse> findOriginalUrl(@PathVariable("shortCode") String shortCode) {
        return new ResponseEntity<>(shortUrlService.findByShortCode(shortCode), HttpStatus.OK);
    }

    @PutMapping(path = "/{shortCode}")
    public ResponseEntity<ShortUrlResponse> updateShortUrl(@PathVariable("shortCode") String shortCode,
                                                           @RequestBody ShortUrlRequest request) {
        return new ResponseEntity<>(shortUrlService.updateShortUrl(shortCode, request), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{shortCode}")
    public ResponseEntity<Void> deleteShortUrl(@PathVariable("shortCode") String shortCode) {
        shortUrlService.deleteShortUrl(shortCode);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{shortCode}/stats")
    public ResponseEntity<ShortUrlStatsResponse> findShortUrlStats(@PathVariable("shortCode") String shortCode) {
        return new ResponseEntity<>(shortUrlService.getShortUrlStats(shortCode), HttpStatus.OK);
    }
}
