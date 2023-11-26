package ru.skypro.homework.mapper;

import ru.skypro.homework.dto.AdCreateOrUpdate;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdInfo;
import ru.skypro.homework.dto.AdsAll;
import ru.skypro.homework.entity.Ad;

import java.util.List;

public class AdMapper {
    public static AdInfo outDtoInfo(Ad ad) {
        AdInfo adInfo = new AdInfo();

        adInfo.setPk(ad.getId());
        adInfo.setAuthorFirstName(ad.getUserEntity().getFirstName());
        adInfo.setAuthorLastName(ad.getUserEntity().getLastName());
        adInfo.setDescription(ad.getDescription());
        adInfo.setEmail(ad.getUserEntity().getLogin());
        adInfo.setImage(String.format("/ads/%d/image",ad.getAdImage().getId()));
        adInfo.setPhone(ad.getUserEntity().getPhone());
        adInfo.setPrice(ad.getPrice());
        adInfo.setTitle(ad.getTitle());

        return adInfo;
    }

    public static AdsAll outDtoAll(List<AdDto> adListDto) {
        AdsAll adsAll = new AdsAll();

        adsAll.setCount(adListDto.size());
        adsAll.setResult(adListDto);

        return adsAll;
    }

    public static AdDto outDtoAd(Ad ad) {
        AdDto adDto = new AdDto();
        adDto.setAuthor(ad.getUserEntity().getId());
        adDto.setImage(String.format("/ads/%d/image",ad.getAdImage().getId()));
        adDto.setPk(ad.getId());
        adDto.setPrice(ad.getPrice());
        adDto.setTitle(ad.getTitle());
        return adDto;
    }

    public static Ad inDtoUpdate(AdCreateOrUpdate adCrtOrUpd) {
        Ad ad = new Ad();

        ad.setTitle(adCrtOrUpd.getTitle());
        ad.setPrice(adCrtOrUpd.getPrice());
        ad.setDescription(adCrtOrUpd.getDescription());

        return ad;
    }

}
