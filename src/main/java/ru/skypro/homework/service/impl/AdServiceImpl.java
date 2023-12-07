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
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.EntityNotFoundException;
import ru.skypro.homework.exception.UnauthorizedUserException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repositories.AdRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.Validation;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing advertisements.
 * @author KodarovSS
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;
    private final Validation validation;

    /**
     * Add a new advertisement.
     *
     * @param auth      The authentication details.
     * @param adCrOrUpd The DTO details of the new advertisement.
     * @param image     The image file for the advertisement.
     * @return AdDto representing the added advertisement.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public AdDto addAd(Authentication auth, AdCreateOrUpdate adCrOrUpd,MultipartFile image) throws IOException {
        log.debug("---started addAd");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName())
                .orElseThrow(()->new UserNotFoundException("User not found with username: " + auth.getName()));
        Ad ad = adMapper.inDtoUpdate(adCrOrUpd);
        AdImage adImage = new AdImage();
        adImage.setData(image.getBytes());

        ad.setAdImage(adImage);
        ad.setUserEntity(userEntity);
        adRepository.save(ad);
        return adMapper.outDtoAd(ad);
    }

    /**
     * Get information about a specific advertisement.
     *
     * @param adId The ID of the advertisement.
     * @return AdInfo representing the information about the advertisement.
     * @throws EntityNotFoundException if the advertisement is not found.
     */
    @Override
    public AdInfo getAd(int adId) {
        log.debug("--- service started getAd");
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement not found with ID: " + adId));
        return adMapper.outDtoInfo(ad);
    }
    /**
     * Update an existing advertisement.
     *
     * @param auth      The authentication details.
     * @param adId      The ID of the advertisement to update.
     * @param adCrOrUpd The DTO updated details of the advertisement.
     * @return AdDto representing the updated advertisement.
     * @throws UserNotFoundException if the user is not found.
     * @throws EntityNotFoundException if the advertisement is not found.
     * @throws UnauthorizedUserException if the user is not authorized to update the ad.
     */
    @Override
    public AdDto updateAd(Authentication auth, int adId, AdCreateOrUpdate adCrOrUpd)
            throws UserNotFoundException,EntityNotFoundException, UnauthorizedUserException {
        log.debug("--- service started updateAd");
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement not found with ID: " + adId));
        if (validation.validateAd(auth,adId)) {
            Ad updatedAd = adMapper.inDtoUpdate(adCrOrUpd,ad);
            return adMapper.outDtoAd(updatedAd);
        } else {
            throw new UnauthorizedUserException("User is not authorized to update this ad");
        }
    }

    /**
     * Delete an advertisement.
     *
     * @param auth The authentication details.
     * @param idAd The ID of the advertisement to delete.
     * @return True if the advertisement is deleted, false otherwise.
     */
    @Override
    public boolean deleteAd(Authentication auth, int idAd) throws UserNotFoundException {
        log.debug("--- service started deleteAd");
        if (adRepository.findById(idAd).isPresent()){
            adRepository.deleteById(idAd);
            return true;
        }
        return false;
    }

    /**
     * Get a list of all advertisements.
     *
     * @return AdsAll representing a list of all advertisements.
     */
    @Override
    public AdsAll getAllAds() {
        log.debug("--- service started getAllAds");
        List<Ad> ads = adRepository.findAll();
        List<AdDto> adDtoList = ads.stream()
                .map(adMapper::outDtoAd)
                .collect(Collectors.toList());
        return adMapper.outDtoAll(adDtoList);
    }

    /**
     * Get a list of advertisements for the authenticated user.
     *
     * @param auth The authentication details.
     * @return AdsAll representing a list of advertisements for the authenticated user.
     * @throws UserNotFoundException if the user is not found.
     */
    @Override
    public AdsAll getAllAdsAuth(Authentication auth) throws UserNotFoundException {
        log.debug("--- service started getAllAdsAuth");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + auth.getName()));
        List<Ad> adList = adRepository.findAdByUserEntity(userEntity);
        List<AdDto> adDtoList = adList.stream().map(adMapper::outDtoAd).collect(Collectors.toList());
        return  adMapper.outDtoAll(adDtoList);
    }

    /**
     * Update the image of an advertisement.
     *
     * @param auth  The authentication details.
     * @param adId    The ID of the advertisement.
     * @param image The new image for the advertisement.
     * @return Byte array representing the updated image.
     * @throws IOException if an I/O error occurs.
     * @throws UserNotFoundException if the user is not found.
     * @throws EntityNotFoundException if the advertisement is not found.
     */
    @Override
    public byte[] updateImageAd(Authentication auth, int adId, MultipartFile image)
            throws IOException,UserNotFoundException,EntityNotFoundException {
        log.debug("--- service started updateImageAd ");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + auth.getName()));
       Ad ad = adRepository.findAdByUserEntity(userEntity).get(adId);
       ad.setAdImage(ad.getAdImage());
       adRepository.save(ad);
        return ad.getAdImage().getData();
    }

    /**
     * Get the image of an advertisement.
     *
     * @param adId The ID of the advertisement.
     * @return Byte array representing the image of the advertisement.
     */
    @Override
    public byte[] getImageAd(int adId)throws EntityNotFoundException {
        log.debug("--- service started getImageAd");
        return adRepository.findById(adId)
                .orElseThrow(() -> new AdNotFoundException("Advertisement not found with ID: " + adId))
                .getAdImage().getData();
    }
}
