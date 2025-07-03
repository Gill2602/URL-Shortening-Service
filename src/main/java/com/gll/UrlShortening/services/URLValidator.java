package com.gll.UrlShortening.services;

import java.net.URI;
import java.net.URISyntaxException;

public final class URLValidator {

    private URLValidator() {
        throw new UnsupportedOperationException("URL Utility class - cannot be instantiated");
    }

    public static boolean isValidUrl(String url) {
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
