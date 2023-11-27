package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdCreateOrUpdate;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdInfo;
import ru.skypro.homework.dto.AdsAll;
import ru.skypro.homework.service.AdService;

public class AdServiceImpl implements AdService {
    @Override
    public AdDto addAd(Authentication auth, MultipartFile image, AdCreateOrUpdate adCrOrUpd) {
        return null;
    }

    @Override
    public AdInfo getAd(int adId) {
        return null;
    }

    @Override
    public AdDto update(Authentication auth, int idAd, AdCreateOrUpdate adCrOrUpd) {
        return null;
    }

    @Override
    public boolean delete(Authentication auth, int idAd) {
        return false;
    }

    @Override
    public AdsAll getAllAds() {
        return null;
    }

    @Override
    public AdsAll getAllAdsAuth(Authentication auth) {
        return null;
    }

    @Override
    public byte[] updateImageAd(Authentication auth, int id, MultipartFile image) {
        return new byte[0];
    }

    @Override
    public byte[] getImageAd(int idAd) {
        return new byte[0];
    }
}
