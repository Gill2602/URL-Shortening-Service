package com.gll.UrlShortening.mappers.impl;

import com.gll.UrlShortening.entities.ShortUrlEntity;
import com.gll.UrlShortening.mappers.Mapper;
import com.gll.UrlShortening.response.ShortUrlResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlMapperImpl implements Mapper<ShortUrlEntity, ShortUrlResponse> {

    private final ModelMapper mapper;

    public ShortUrlMapperImpl(final ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ShortUrlEntity mapTo(ShortUrlResponse shortUrlResponse) {
        return mapper.map(shortUrlResponse, ShortUrlEntity.class);
    }

    @Override
    public ShortUrlResponse mapFrom(ShortUrlEntity shortUrl) {
        return mapper.map(shortUrl, ShortUrlResponse.class);
    }
}
