package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdCreateOrUpdate;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdInfo;
import ru.skypro.homework.dto.AdsAll;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;

import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {

    /**
     * Добавление объявления
     *
     * @param image
     * @param adCreateOrUpdate
     * @return
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> addAd(@RequestPart("image") MultipartFile image,
                                       @RequestPart("properties") AdCreateOrUpdate adCreateOrUpdate) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new AdDto());
    }

    /**
     * Получение информации об объявлении
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public AdInfo getAd(@PathVariable Integer id) {
        return new AdInfo();
    }

    /**
     * Обновление информации об объявлении
     *
     * @param id
     * @param adCreateOrUpdate
     * @return
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<AdDto> updateAd(@PathVariable Integer id,
                                          @RequestBody AdCreateOrUpdate adCreateOrUpdate) {
        return ResponseEntity.ok(new AdDto());
    }

    /**
     * Удаление объявления
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAd(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Получение всех объявлений
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<AdsAll> getAllAds() {
        return ResponseEntity.ok(new AdsAll());
    }

    /**
     * Получение объявлений авторизованного пользователя
     *
     * @return
     */
    @GetMapping(value = "/me")
    public ResponseEntity<AdsAll> getAdsMe() {
        return ResponseEntity.ok(new AdsAll());
    }

    /**
     * Обновление картинки объявления
     * @param id
     * @param image
     * @return
     * @throws IOException
     */
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id, @RequestPart("image") MultipartFile image) throws IOException {
        try {
            return ResponseEntity.ok(image.getBytes()); //заглушка
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Вспомогательный эндпоинт получения картинки объявления
     * @param idAd
     * @return
     */
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImageAd(@PathVariable("id") Integer idAd){
        //пока без бизнес-логики
        AdImage adImage = new AdImage();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(adImage.getMediaType()));
        headers.setContentLength(adImage.getData().length);
        return ResponseEntity.ok().headers(headers).body(adImage.getData());
    }
}
