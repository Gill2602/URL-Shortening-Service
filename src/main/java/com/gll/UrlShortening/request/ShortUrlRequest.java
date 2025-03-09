package com.gll.UrlShortening.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShortUrlRequest {

    private String url;
}
