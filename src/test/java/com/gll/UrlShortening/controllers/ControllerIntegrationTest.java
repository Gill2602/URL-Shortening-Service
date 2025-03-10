package com.gll.UrlShortening.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gll.UrlShortening.TestDataUtils;
import com.gll.UrlShortening.entities.ShortUrlEntity;
import com.gll.UrlShortening.repositories.ShortUrlRepository;
import com.gll.UrlShortening.request.ShortUrlRequest;
import com.gll.UrlShortening.services.ShortUrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ControllerIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;
    private final ShortUrlService urlService;
    private final ShortUrlRepository urlRepository;

    @Autowired
    public ControllerIntegrationTest(final MockMvc mockMvc,
                                     final ObjectMapper mapper,
                                     final ShortUrlService urlService,
                                     final ShortUrlRepository urlRepository) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
        this.urlService = urlService;
        this.urlRepository = urlRepository;
    }

    @Test
    @DisplayName("Creation: Valid Short URL returns status 201")
    void testCreateValidShortUrl_Returns201() throws Exception {
        ShortUrlRequest request = TestDataUtils.getSomeValidRequest().getFirst();
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    @DisplayName("Creation: Invalid Short URL returns status 400")
    void testCreateInvalidShortUrl_Returns400() throws Exception {
        ShortUrlRequest request = TestDataUtils.getSomeInvalidRequest().getLast();
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    @DisplayName("Retrieval: Existing Short URL returns status 200")
    void testRetrieveExistingShortUrl_Returns200() throws Exception {
        ShortUrlRequest request = TestDataUtils.getSomeValidRequest().getFirst();
        urlService.createShortUrl(request);

        ShortUrlEntity created = urlRepository
                .findLastRecordCreated().orElseThrow(Exception::new);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/shorten/" + created.getShortCode())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Retrieval: Non-existing Short URL returns status 404")
    void testRetrieveNonExistingShortUrl_Returns404() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/shorten/L0L")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    @DisplayName("Update: Existing Short URL with valid data returns status 200")
    void testUpdateExistingShortUrl_ValidData_Returns200() throws Exception {
        ShortUrlRequest request = TestDataUtils.getSomeValidRequest().getFirst();
        urlService.createShortUrl(request);

        ShortUrlEntity created = urlRepository
                .findLastRecordCreated().orElseThrow(Exception::new);

        request.setUrl("https://roadmap.sh/projects/url-shortening-service");
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/shorten/" + created.getShortCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Update: Existing Short URL with invalid URL returns status 400")
    void testUpdateExistingShortUrl_InvalidUrl_Returns400() throws Exception {
        ShortUrlRequest request = TestDataUtils.getSomeValidRequest().getFirst();
        urlService.createShortUrl(request);

        ShortUrlEntity created = urlRepository
                .findLastRecordCreated().orElseThrow(Exception::new);

        request.setUrl("htt/roadmap.sh/projects/url-shortening-service");
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/shorten/" + created.getShortCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    @DisplayName("Update: Non-existing Short URL returns status 404")
    void testUpdateNonExistingShortUrl_Returns404() throws Exception {
        ShortUrlRequest request = new ShortUrlRequest("https://roadmap.sh/projects/url-shortening-service");
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/shorten/" + "L4l")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    @DisplayName("Deletion: Existing Short URL returns status 204")
    void testDeleteExistingShortUrl_Returns204() throws Exception {
        ShortUrlRequest request = TestDataUtils.getSomeValidRequest().getFirst();
        urlService.createShortUrl(request);

        ShortUrlEntity created = urlRepository
                .findLastRecordCreated().orElseThrow(Exception::new);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/shorten/" + created.getShortCode())
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    @DisplayName("Deletion: Non-existing Short URL returns status 404")
    void testDeleteNonExistingShortUrl_Returns404() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/shorten/" + "L4l")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    @DisplayName("Statistics: Request for existing Short URL stats returns status 200")
    void testRetrieveStats_ExistingShortUrl_Returns200() throws Exception {
        ShortUrlRequest request = TestDataUtils.getSomeValidRequest().getFirst();
        urlService.createShortUrl(request);

        ShortUrlEntity created = urlRepository
                .findLastRecordCreated().orElseThrow(Exception::new);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/shorten/" + created.getShortCode() + "/stats")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @DisplayName("Statistics: Request for non-existing Short URL stats returns status 404")
    void testRetrieveStats_NonExistingShortUrl_Returns404() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/shorten/L4l/stats")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
