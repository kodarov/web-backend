package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentCreateOrUpdate;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repositories.AdRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDto addComment(Authentication auth, int adId, CommentCreateOrUpdate comCreOrUpd) {
        log.info("сервис метод addComment");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();
        Ad ad = adRepository.findById(adId).orElseThrow();
        Comment newComment = CommentMapper.inDto(userEntity, ad, comCreOrUpd);
        return CommentMapper.outDto(commentRepository.save(newComment));
    }

    @Override
    public Comments getComments(Authentication auth, int adId) {
        log.info("сервис метод getComments");
        List<Comment> commentList = commentRepository.findCommentsByAd_Id(adId);
        return CommentMapper.outDtos(commentList);
    }

    @Override
    public CommentDto updateComment(Authentication auth, int adId, int commentId, CommentCreateOrUpdate comCreOrUpd) {
        log.info("сервис метод updateComment");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();
        Ad ad = adRepository.findById(adId).orElseThrow();
        Comment currentComment = commentRepository.findById(commentId).orElseThrow();
        if(userEntity.equals(currentComment.getUserEntity()))
        {
            Comment newComment = CommentMapper.inDto(userEntity,ad,comCreOrUpd);
            newComment.setId(currentComment.getId()); // пока так, DTO переделать надо
            return CommentMapper.outDto(commentRepository.save(newComment));
        }
        return  null; //добавить исключение!!!
    }

    @Override
    public boolean deleteComment(Authentication auth, int adId, int commentId) {
        log.info("сервис метод updateComment");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();
        Ad ad = adRepository.findById(adId).orElseThrow();
        if(userEntity.equals(ad.getUserEntity())){
           commentRepository.deleteById(commentId);
           return true;
        }
        return false;
    }
}
