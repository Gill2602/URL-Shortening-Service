package com.gll.UrlShortening.controllers;

import com.gll.UrlShortening.dtos.ShortenedUrlDTO;
import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import com.gll.UrlShortening.mappers.Mapper;
import com.gll.UrlShortening.request.CreateShortUrlRequest;
import com.gll.UrlShortening.services.ShortenedUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/shorten")
public class UrlShorteningController {

    private final ShortenedUrlService shortenedUrlService;
    private final Mapper<ShortenedUrlEntity, ShortenedUrlDTO> mapper;

    @Autowired
    public UrlShorteningController(final ShortenedUrlService shortenedUrlService,
                                   final Mapper<ShortenedUrlEntity, ShortenedUrlDTO> mapper) {
        this.shortenedUrlService = shortenedUrlService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ShortenedUrlDTO> createShortUrl(@RequestBody CreateShortUrlRequest request) {

        try {
            ShortenedUrlEntity shortenedUrl = shortenedUrlService.createShortUrl(request);
            ShortenedUrlDTO shortenedUrlDTO = mapper.mapFrom(shortenedUrl);

            return new ResponseEntity<>(shortenedUrlDTO, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
