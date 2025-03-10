package com.gll.UrlShortening.repositories;

import com.gll.UrlShortening.TestDataUtils;
import com.gll.UrlShortening.entities.ShortUrlEntity;
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

    private final ShortUrlRepository shortenedUrlRepository;

    @Autowired
    public RepositoryIntegrationTest(final ShortUrlRepository shortenedUrlRepository) {
        this.shortenedUrlRepository = shortenedUrlRepository;
    }

    @Test
    void findLastRecordCreated() {
        List<ShortUrlEntity> urlEntities = TestDataUtils.getSomeShortenedUrl();
        shortenedUrlRepository.saveAll(urlEntities);

        Optional<ShortUrlEntity> optionalResult = shortenedUrlRepository.findLastRecordCreated();
        assertThat(optionalResult).isPresent();

        ShortUrlEntity result = optionalResult.get();
        assertThat(result).isEqualTo(urlEntities.getLast());
    }

    @Test
    void findByShortenedCode() {
        List<ShortUrlEntity> urlEntities = TestDataUtils.getSomeShortenedUrl();
        shortenedUrlRepository.saveAll(urlEntities);

        Optional<ShortUrlEntity> failResult = shortenedUrlRepository.findByShortCode("L0L");
        assertThat(failResult).isNotPresent();

        Optional<ShortUrlEntity> optionalResult = shortenedUrlRepository.findByShortCode("2B");
        assertThat(optionalResult).isPresent();

        ShortUrlEntity result = optionalResult.get();
        assertThat(result).isEqualTo(urlEntities.get(1));
    }

    @Test
    void existsByOriginalUrl() {
        List<ShortUrlEntity> urlEntities = TestDataUtils.getSomeShortenedUrl();
        shortenedUrlRepository.saveAll(urlEntities);

        assertThat(shortenedUrlRepository.existsByUrl("https://spring.io/")).isFalse();
        assertThat(shortenedUrlRepository.existsByUrl("https://www.youtube.com/")).isTrue();
    }
}
