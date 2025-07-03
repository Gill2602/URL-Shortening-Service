package com.gll.UrlShortening.mappers.impl;

import com.gll.UrlShortening.entities.ShortUrlEntity;
import com.gll.UrlShortening.mappers.Mapper;
import com.gll.UrlShortening.responses.ShortUrlStatsResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlStatsMapperImpl implements Mapper<ShortUrlEntity, ShortUrlStatsResponse> {

    private final ModelMapper mapper;

    public ShortUrlStatsMapperImpl(final ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ShortUrlEntity mapTo(ShortUrlStatsResponse shortUrlStatsResponse) {
        return mapper.map(shortUrlStatsResponse, ShortUrlEntity.class);
    }

    @Override
    public ShortUrlStatsResponse mapFrom(ShortUrlEntity shortUrl) {
        return mapper.map(shortUrl, ShortUrlStatsResponse.class);
    }
}
