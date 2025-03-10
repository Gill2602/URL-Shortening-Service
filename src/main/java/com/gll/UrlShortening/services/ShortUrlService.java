package com.gll.UrlShortening.services;

import com.gll.UrlShortening.entities.ShortUrlEntity;
import com.gll.UrlShortening.request.ShortUrlRequest;

public interface ShortUrlService {

    ShortUrlEntity createShortUrl(ShortUrlRequest request);

    ShortUrlEntity findByShortCode(String shortCode);

    ShortUrlEntity getShortUrlStats(String shortCode);

    ShortUrlEntity updateShortUrl(String shortCode, ShortUrlRequest request);

    void deleteShortUrl(String shortCode);
}
