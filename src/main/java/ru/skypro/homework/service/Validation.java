package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;

import java.security.Principal;

public interface Validation {
    boolean validateComment(Authentication auth, int commentId);
    boolean validateAd(Authentication auth,int adId);
}
