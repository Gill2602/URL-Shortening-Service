package com.gll.UrlShortening.services;

import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import com.gll.UrlShortening.request.ShortUrlRequest;

public interface ShortenedUrlService {

    ShortenedUrlEntity createShortUrl(ShortUrlRequest request);

    ShortenedUrlEntity retrieveByShortUrl(String shortCode);

    ShortenedUrlEntity getStatsByShortUrl(String shortCode);

    ShortenedUrlEntity updateShortUrl(String shortCode, ShortUrlRequest request);

    void deleteShortUrl(String shortCode);
}
