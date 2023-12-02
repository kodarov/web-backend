package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repositories.AdRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.service.Validation;

import java.security.Principal;

/**
 * Сервис проверки прав на редактирование
 * коммента и объявления
 * @author Salavat Kodarov
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidationImpl implements Validation {
    private final CommentRepository commentRepository;
    private final AdRepository adRepository;

    @Override
    public boolean validateComment(Authentication auth, int commentId) {
        if (!commentRepository.existsById(commentId)){
            return false;
        }
            Comment comment = commentRepository.findById(commentId).orElseThrow();
            UserEntity userEntity = comment.getUserEntity();
            return userEntity.getLogin().equals(auth.getName())
                    || auth.getAuthorities().stream()
                    .anyMatch(e->e.getAuthority().equals("ROLE_ADMIN"));
    }

    @Override
    public boolean validateAd(Authentication auth,int adId) {
        if (!adRepository.existsById(adId)){
            return false;
        }
        Ad ad = adRepository.findById(adId).orElseThrow();
        UserEntity userEntity = ad.getUserEntity();
        return userEntity.getLogin().equals(auth.getName())
                || auth.getAuthorities().stream()
                .anyMatch(e->e.getAuthority().equals("ROLE_ADMIN"));
    }
}
