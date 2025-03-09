package com.gll.UrlShortening.repositories;

import com.gll.UrlShortening.TestDataUtils;
import com.gll.UrlShortening.entities.ShortenedUrlEntity;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RepositoryIntegrationTest {

    private final ShortenedUrlRepository shortenedUrlRepository;

    @Autowired
    public RepositoryIntegrationTest(final ShortenedUrlRepository shortenedUrlRepository) {
        this.shortenedUrlRepository = shortenedUrlRepository;
    }

    @Test
    void findLastRecordCreated() {
        List<ShortenedUrlEntity> urlEntities = TestDataUtils.getSomeShortenedUrl();
        shortenedUrlRepository.saveAll(urlEntities);

        Optional<ShortenedUrlEntity> optionalResult = shortenedUrlRepository.findLastRecordCreated();
        assertThat(optionalResult).isPresent();

        ShortenedUrlEntity result = optionalResult.get();
        assertThat(result).isEqualTo(urlEntities.getLast());
    }

    @Test
    void findByShortenedCode() {
        List<ShortenedUrlEntity> urlEntities = TestDataUtils.getSomeShortenedUrl();
        shortenedUrlRepository.saveAll(urlEntities);

        Optional<ShortenedUrlEntity> failResult = shortenedUrlRepository.findByShortenedCode("L0L");
        assertThat(failResult).isNotPresent();

        Optional<ShortenedUrlEntity> optionalResult = shortenedUrlRepository.findByShortenedCode("2B");
        assertThat(optionalResult).isPresent();

        ShortenedUrlEntity result = optionalResult.get();
        assertThat(result).isEqualTo(urlEntities.get(1));
    }

    @Test
    void existsByOriginalUrl() {
        List<ShortenedUrlEntity> urlEntities = TestDataUtils.getSomeShortenedUrl();
        shortenedUrlRepository.saveAll(urlEntities);

        assertThat(shortenedUrlRepository.existsByOriginalUrl("https://spring.io/")).isFalse();
        assertThat(shortenedUrlRepository.existsByOriginalUrl("https://www.youtube.com/")).isTrue();
    }
}
