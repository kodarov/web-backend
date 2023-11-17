package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdInfo;
import ru.skypro.homework.dto.AdsAll;
import ru.skypro.homework.dto.AdCreateOrUpdate;

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
     * @param ad
     * @return
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> addAd(@RequestPart("image") MultipartFile image, @RequestPart AdDto ad) {
        log.info("ad added");
        return ResponseEntity.status(HttpStatus.CREATED).body(new AdDto());
    }

    /**
     * Получение информации об объявлении
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public AdInfo getAd(@PathVariable Integer id){
        return new AdInfo();
    }

    /**
     * Обновление информации об объявлении
     * @param id
     * @param adCreateOrUpdate
     * @return
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<AdDto> updateAd(@PathVariable Integer id,
                                                     @RequestBody AdCreateOrUpdate adCreateOrUpdate){
        return ResponseEntity.ok(new AdDto());
    }

    /**
     * Удаление объявления
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAd(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Получение всех объявлений
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<AdsAll> getAllAds() {
        return ResponseEntity.ok().body(new AdsAll());
    }
}
