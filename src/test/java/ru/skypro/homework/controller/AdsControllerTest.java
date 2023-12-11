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

import java.net.URI;

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
        adDto.setPrice(adCrOrUpd.getPrice());;
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

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED).isNotNull();
        Assertions.assertThat(response.getBody()).isEqualTo(adDto);
    }

    @Test
    void getAdTestPositive() throws Exception{
        URI newUrl = new URI(baseUrl + "/1");
        ResponseEntity<AdInfo> response = restTemplate.getForEntity(newUrl,AdInfo.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
        Assertions.assertThat(response.getBody()).isInstanceOf(AdInfo.class);
    }
    @Test
    void getAdTestNegativeNOT_FOUND() throws Exception{
        URI newUrl = new URI(baseUrl + "/100");
        ResponseEntity<?> response = restTemplate.getForEntity(newUrl,String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND).isNotNull();
    }
    @Test
    void updateAdTestPositive() throws Exception{
        URI newUrl = new URI(baseUrl + "/1");
        AdCreateOrUpdate adCrOrUpd = new AdCreateOrUpdate();
        adCrOrUpd.setTitle("Mercedes-Benz");
        adCrOrUpd.setPrice(1000000);
        adCrOrUpd.setDescription("Mercedes Benz is the best car for family and work.");

        //ResponseEntity<AdDto> response = restTemplate.patchForObject(baseUrl+"ç",adCrOrUpd,AdDto.class,1);
    }

}