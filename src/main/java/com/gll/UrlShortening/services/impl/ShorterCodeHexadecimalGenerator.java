package com.gll.UrlShortening.services.impl;

import com.gll.UrlShortening.services.ShorterCodeGenerator;
import org.springframework.stereotype.Service;

@Service
public class ShorterCodeHexadecimalGenerator implements ShorterCodeGenerator {
    private long counter = 0;

    public String next() {
        return Long.toHexString(counter++).toUpperCase();
    }
}
