package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdCreateOrUpdate;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdInfo;
import ru.skypro.homework.dto.AdsAll;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repositories.AdRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AdService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;
    @Override
    public AdDto addAd(Authentication auth, AdCreateOrUpdate adCrOrUpd,MultipartFile image) throws IOException {
        log.info("сервис addAd");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();
        Ad ad = adMapper.inDtoUpdate(adCrOrUpd);
        AdImage adImage = new AdImage();
        adImage.setData(image.getBytes());

        ad.setAdImage(adImage);
        ad.setUserEntity(userEntity);
        adRepository.save(ad);
        return adMapper.outDtoAd(ad);
    }

    @Override
    public AdInfo getAd(int adId) {
        Ad ad = adRepository.findById(adId).orElseThrow();
        return adMapper.outDtoInfo(ad);
    }

    @Override
    public AdDto updateAd(Authentication auth, int idAd, AdCreateOrUpdate adCrOrUpd) throws Exception {
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();
        Ad ad = adRepository.findById(idAd).orElseThrow();
        if (userEntity.getId() == ad.getUserEntity().getId()) {
            Ad updatedAd = adMapper.inDtoUpdate(adCrOrUpd,ad);
            return adMapper.outDtoAd(updatedAd);
        } else {
            throw new Exception(); //временно
        }
    }

    @Override
    public boolean delete(Authentication auth, int idAd) {
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();
        if (adRepository.findById(idAd).isPresent()){
            adRepository.deleteById(idAd);
            return true;
        }
        return false;
    }

    @Override
    public AdsAll getAllAds() {
        List<Ad> ads = adRepository.findAll();
        List<AdDto> adDtoList = ads.stream()
                .map(adMapper::outDtoAd)
                .collect(Collectors.toList());
        return adMapper.outDtoAll(adDtoList);
    }

    @Override
    public AdsAll getAllAdsAuth(Authentication auth) {
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();
        List<Ad> adList = adRepository.findAdByUserEntity(userEntity);
        List<AdDto> adDtoList = adList.stream().map(adMapper::outDtoAd).collect(Collectors.toList()); // Переделать маппер adList To AdsAll
        return  adMapper.outDtoAll(adDtoList);
    }

    @Override
    public byte[] updateImageAd(Authentication auth, int id, MultipartFile image) throws IOException {
        log.debug("--- service started updateImageAd");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();
       Ad ad = adRepository.findAdByUserEntity(userEntity).get(id);
       ad.setAdImage(ad.getAdImage());
       adRepository.save(ad);
        return ad.getAdImage().getData();
    }

    @Override
    public byte[] getImageAd(int idAd) {
        return adRepository.findById(idAd).orElseThrow().getAdImage().getData();
    }
}
