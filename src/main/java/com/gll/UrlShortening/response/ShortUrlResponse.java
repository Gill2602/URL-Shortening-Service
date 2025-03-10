package com.gll.UrlShortening.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ShortUrlResponse {

    private Long id;

    private String url;
    private String shortCode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
