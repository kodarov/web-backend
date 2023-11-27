package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdCreateOrUpdate;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdInfo;
import ru.skypro.homework.dto.AdsAll;

public interface AdService {
    AdDto addAd(Authentication auth,MultipartFile image, AdCreateOrUpdate adCrOrUpd);
    AdInfo getAd(int adId);
    AdDto update(Authentication auth, int idAd, AdCreateOrUpdate adCrOrUpd);
    boolean delete(Authentication auth, int idAd);
    AdsAll getAllAds();
    AdsAll getAllAdsAuth(Authentication auth);
    byte[] updateImageAd(Authentication auth, int id, MultipartFile image);
    byte[] getImageAd(int idAd);

}
