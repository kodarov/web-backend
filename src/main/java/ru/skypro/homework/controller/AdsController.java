package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdCreateOrUpdate;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdInfo;
import ru.skypro.homework.dto.AdsAll;
import ru.skypro.homework.service.AdService;

import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {
    private  final AdService adService;

    /**
     * Добавление объявления
     *
     * @param image
     * @param adCreateOrUpdate
     * @return
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> addAd(@RequestPart("properties") AdCreateOrUpdate adCreateOrUpdate,
                                       @RequestPart("image") MultipartFile image) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AdDto adDto = adService.addAd(auth,adCreateOrUpdate,image);
        return ResponseEntity.status(HttpStatus.CREATED).body(adDto);
    }

    /**
     * Получение информации об объявлении
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public AdInfo getAd(@PathVariable Integer id) {
        AdInfo adInfo = adService.getAd(id);
        return adInfo;
    }

    /**
     * Обновление информации об объявлении
     *
     * @param id
     * @param adCreateOrUpdate
     * @return
     */
    @PreAuthorize("@validationImpl.validateAd(authentication,#id)")
    @PatchMapping(value = "/{id}")
    public AdDto updateAd(@PathVariable Integer id,
                          @RequestBody AdCreateOrUpdate adCreateOrUpdate) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AdDto adDto = adService.updateAd(auth,id,adCreateOrUpdate);
        return adDto;
    }

    /**
     * Удаление объявления
     *
     * @param id
     * @return
     */
    @PreAuthorize("@validationImpl.validateAd(authentication,#id)")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAd(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (adService.delete(auth,id)){
           return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Получение всех объявлений
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<AdsAll> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    /**
     * Получение объявлений авторизованного пользователя
     *
     * @return
     */
    @GetMapping(value = "/me")
    public ResponseEntity<AdsAll> getAdsMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(adService.getAllAdsAuth(auth));
    }

    /**
     * Обновление картинки объявления
     * @param id
     * @param image
     * @return
     * @throws IOException
     */
    @PatchMapping(value = "/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id, @RequestPart("image") MultipartFile image) throws IOException {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return ResponseEntity.ok(adService.updateImageAd(auth,id,image));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Вспомогательный эндпоинт получения картинки объявления
     * @param idAd
     * @return
     */
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImageAd(@PathVariable("id") Integer idAd){
        return ResponseEntity.ok().body(adService.getImageAd(idAd));
    }
}
