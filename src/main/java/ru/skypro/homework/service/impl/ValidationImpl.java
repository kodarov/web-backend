package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repositories.AdRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.service.Validation;

/**
 * Service for checking permissions to edit comments and advertisements.
 *
 * <p>This service provides methods to validate whether a user has the right
 * to edit a comment or an advertisement.</p>
 *
 * @author KodarovSS
 * @version 1.0
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidationImpl implements Validation {
    private final CommentRepository commentRepository;
    private final AdRepository adRepository;

    /**
     * Check user's permissions to edit a comment.
     *
     * @param auth      Authentication details.
     * @param commentId Comment ID.
     * @return True if the user has editing rights, false otherwise.
     */
    @Override
    public boolean validateComment(Authentication auth, int commentId) {
        if (!commentRepository.existsById(commentId)){
            return false;
        }
            Comment comment = commentRepository.findById(commentId).orElseThrow();
            UserEntity userEntity = comment.getUserEntity();
            return isUserAllowedToEdit(auth, userEntity);
    }

    /**
     * Check user's permissions to edit an advertisement.
     *
     * @param auth Authentication details.
     * @param adId Advertisement ID.
     * @return True if the user has editing rights, false otherwise.
     */
    @Override
    public boolean validateAd(Authentication auth,int adId) {
        if (!adRepository.existsById(adId)){
            return false;
        }
        Ad ad = adRepository.findById(adId).orElseThrow();
        UserEntity userEntity = ad.getUserEntity();
        return isUserAllowedToEdit(auth, userEntity);
    }

    /**
     * Check whether the user is allowed to edit based on their role and ownership.
     *
     * @param auth      Authentication details.
     * @param userEntity UserEntity representing the owner of the entity.
     * @return True if the user has editing rights, false otherwise.
     */
    private boolean isUserAllowedToEdit(Authentication auth, UserEntity userEntity) {
        log.debug("Auth username: {}", auth.getName());
        log.debug("UserEntity username: {}", userEntity.getLogin());
        return userEntity.getLogin().equals(auth.getName())
                || auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
}
