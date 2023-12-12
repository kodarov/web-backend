package ru.skypro.homework.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.skypro.homework.dto.AdCreateOrUpdate;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdInfo;
import ru.skypro.homework.dto.AdsAll;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdsControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private AdsController adsController;
    @Autowired
    private TestRestTemplate restTemplate;
    private String baseUrl;
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }

    @BeforeEach
    public void setUp() throws Exception {
        baseUrl = "http://localhost:" + port + "/ads";
        restTemplate = restTemplate.withBasicAuth("kodarov@gmail.com", "password");
    }

    @Test
    void addAdTestPositive() throws Exception {
        Resource imageResource = new ClassPathResource("/images/test-image.jpg");
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new FileSystemResource(imageResource.getFile()));

        AdCreateOrUpdate adCrOrUpd = new AdCreateOrUpdate();
        adCrOrUpd.setTitle("Mercedes-Benz");
        adCrOrUpd.setPrice(1000000);
        adCrOrUpd.setDescription("Mercedes Benz is the best car for family and work.");
        body.add("properties", adCrOrUpd);

        AdDto adDto = new AdDto();
        adDto.setTitle(adCrOrUpd.getTitle());
        adDto.setPrice(adCrOrUpd.getPrice());
        ;
        adDto.setAuthor(1);
        adDto.setPk(3);
        adDto.setImage("/ads/3/image");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<AdDto> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                AdDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED).isNotNull();
        assertThat(response.getBody()).isEqualTo(adDto);
    }

    @Test
    void getAdTestPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/1");
        ResponseEntity<AdInfo> response = restTemplate.getForEntity(newUrl, AdInfo.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
        assertThat(response.getBody()).isInstanceOf(AdInfo.class);
    }

    @Test
    void getAdTestNegativeNOT_FOUND() throws Exception {
        URI newUrl = new URI(baseUrl + "/100");
        ResponseEntity<?> response = restTemplate.getForEntity(newUrl, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND).isNotNull();
    }

    @Test
    void updateAdTestPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/1");

        AdCreateOrUpdate adCrOrUpd = new AdCreateOrUpdate();
        adCrOrUpd.setTitle("Mercedes-Benz");
        adCrOrUpd.setPrice(1000000);
        adCrOrUpd.setDescription("Mercedes Benz is the best car for family and work.");

        AdDto adDto = new AdDto();
        adDto.setTitle(adCrOrUpd.getTitle());
        adDto.setPrice(adCrOrUpd.getPrice());
        adDto.setPk(1);
        adDto.setImage("/ads/1/image");
        adDto.setAuthor(1);

        ResponseEntity<?> response = restTemplate
                .exchange(
                        newUrl,
                        HttpMethod.PATCH,
                        new HttpEntity<>(adCrOrUpd),
                        AdDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(adDto);
    }

    @Test
    void updateAdTestNegativeFORBIDDEN() throws Exception {
        URI newUrl = new URI(baseUrl + "/2");

        AdCreateOrUpdate adCrOrUpd = new AdCreateOrUpdate();
        adCrOrUpd.setTitle("Mercedes-Benz");
        adCrOrUpd.setPrice(1000000);
        adCrOrUpd.setDescription("Mercedes Benz is the best car for family and work.");

        AdDto adDto = new AdDto();
        adDto.setTitle(adCrOrUpd.getTitle());
        adDto.setPrice(adCrOrUpd.getPrice());
        adDto.setPk(1);
        adDto.setImage("/ads/1/image");
        adDto.setAuthor(1);

        ResponseEntity<?> response = restTemplate
                .exchange(
                        newUrl,
                        HttpMethod.PATCH,
                        new HttpEntity<>(adCrOrUpd),
                        AdDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void deleteAdTestPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/2");
        ResponseEntity<?> response = restTemplate
                .withBasicAuth("testuser@gmail.com", "password")
                .exchange(
                        newUrl,
                        HttpMethod.DELETE,
                        HttpEntity.EMPTY,
                        String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getAllAdsTestPositive() throws Exception {
        ResponseEntity<AdsAll> response = restTemplate.getForEntity(baseUrl, AdsAll.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
        assertThat(response.getBody()).isInstanceOf(AdsAll.class);
    }

    @Test
    void getAdsMePositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/me");

        ResponseEntity<AdsAll> response = restTemplate.getForEntity(newUrl, AdsAll.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
        assertThat(response.getBody()).isInstanceOf(AdsAll.class);
    }

    @Test
    void updateAdImagePositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/1/image");
        Resource imageResource = new ClassPathResource("/images/test-image.jpg");
        long imageSizeIn = imageResource.getFile().length();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new FileSystemResource(imageResource.getFile()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(
                newUrl,
                HttpMethod.PATCH,
                request,
                byte[].class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();

        //get imageAd
        URI getImageAdUrl = new URI(baseUrl + "/1/image");
        ResponseEntity<byte[]> responseGetImageAd = restTemplate.getForEntity(getImageAdUrl, byte[].class);
        Assertions.assertThat(responseGetImageAd.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
        Assertions.assertThat(responseGetImageAd.getBody().length).isEqualTo(imageSizeIn);
    }

    @Test
    void getAdImageTestPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/1/image");
        ResponseEntity<byte[]> response = restTemplate.getForEntity(newUrl, byte[].class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
    }
}