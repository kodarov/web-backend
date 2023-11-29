package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdCreateOrUpdate;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdInfo;
import ru.skypro.homework.dto.AdsAll;
import ru.skypro.homework.entity.Ad;

import java.util.List;
@Component
public class AdMapper {
    public AdInfo outDtoInfo(Ad ad) {
        AdInfo adInfo = new AdInfo();

        adInfo.setPk(ad.getId());
        adInfo.setAuthorFirstName(ad.getUserEntity().getFirstName());
        adInfo.setAuthorLastName(ad.getUserEntity().getLastName());
        adInfo.setDescription(ad.getDescription());
        adInfo.setEmail(ad.getUserEntity().getLogin());
        adInfo.setImage(String.format("/ads/image/%d",ad.getAdImage().getId()));
        adInfo.setPhone(ad.getUserEntity().getPhone());
        adInfo.setPrice(ad.getPrice());
        adInfo.setTitle(ad.getTitle());

        return adInfo;
    }

    public AdsAll outDtoAll(List<AdDto> adListDto) {
        AdsAll adsAll = new AdsAll();

        adsAll.setCount(adListDto.size());
        adsAll.setResults(adListDto);

        return adsAll;
    }

    public AdDto outDtoAd(Ad ad) {
        AdDto adDto = new AdDto();
        adDto.setAuthor(ad.getUserEntity().getId());
        adDto.setImage(String.format("/ads/image/%d",ad.getAdImage().getId()));
        adDto.setPk(ad.getId());
        adDto.setPrice(ad.getPrice());
        adDto.setTitle(ad.getTitle());
        return adDto;
    }

    public Ad inDtoUpdate(AdCreateOrUpdate adCrtOrUpd) {
        Ad ad = new Ad();
        ad.setTitle(adCrtOrUpd.getTitle());
        ad.setPrice(adCrtOrUpd.getPrice());
        ad.setDescription(adCrtOrUpd.getDescription());
        return ad;
    }
    public Ad inDtoUpdate(AdCreateOrUpdate adCrtOrUpd, Ad ad) {
        ad.setTitle(adCrtOrUpd.getTitle());
        ad.setPrice(adCrtOrUpd.getPrice());
        ad.setDescription(adCrtOrUpd.getDescription());
        return ad;
    }

}
