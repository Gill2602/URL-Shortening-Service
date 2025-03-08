package com.gll.UrlShortening.mappers.impl;

import com.gll.UrlShortening.dtos.ShortenedUrlDTO;
import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import com.gll.UrlShortening.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShortenedURLMapper implements Mapper<ShortenedUrlEntity, ShortenedUrlDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public ShortenedURLMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ShortenedUrlDTO mapFrom(ShortenedUrlEntity shortenedUrlEntity) {
        return modelMapper.map(shortenedUrlEntity, ShortenedUrlDTO.class);
    }

    @Override
    public ShortenedUrlEntity mapTo(ShortenedUrlDTO shortenedUrlDTO) {
        return modelMapper.map(shortenedUrlDTO, ShortenedUrlEntity.class);
    }
}
