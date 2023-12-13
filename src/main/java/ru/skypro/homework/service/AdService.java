package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdCreateOrUpdate;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdInfo;
import ru.skypro.homework.dto.AdsAll;

import java.io.IOException;

public interface AdService {
    AdDto addAd(Authentication auth, AdCreateOrUpdate adCrOrUpd, MultipartFile image) throws IOException;
    AdInfo getAd(int adId);
    AdDto updateAd(Authentication auth, int idAd, AdCreateOrUpdate adCrOrUpd) throws Exception;
    boolean deleteAd(Authentication auth, int idAd);
    AdsAll getAllAds();
    AdsAll getAllAdsAuth(Authentication auth);
    byte[] updateImageAd(Authentication auth, int id, MultipartFile image) throws IOException;
    byte[] getImageAd(int idAd);

}
