package com.gll.UrlShortening.services.impl;

import com.gll.UrlShortening.services.UrlValidator;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class URLValidatorImpl implements UrlValidator {

    @Override
    public boolean isValidUrl(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }

        try {
            URI uri = new URI(url);
            return uri.getScheme() != null && uri.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
