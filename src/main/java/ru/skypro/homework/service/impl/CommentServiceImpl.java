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
import ru.skypro.homework.exception.EntityNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repositories.AdRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;

/**
 * Service for managing comments on advertisements.
 * @author KodarovSS
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    /**
     * Add a new comment to an advertisement.
     *
     * @param auth        Authentication details.
     * @param adId        Advertisement ID.
     * @param comCreOrUpd DTO with information for creating or updating a comment.
     * @return DTO of the new comment.
     */
    @Override
    public CommentDto addComment(Authentication auth, int adId, CommentCreateOrUpdate comCreOrUpd) {
        log.debug("--- service started addComment");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName())
                .orElseThrow(()->new UserNotFoundException("User not found: " + auth.getName()));
        Ad ad = adRepository.findById(adId)
                .orElseThrow(()->new EntityNotFoundException("Advertisement not found with ID: " + adId));
        Comment newComment = commentMapper.inDto(userEntity, ad, comCreOrUpd);
        return commentMapper.outDto(commentRepository.save(newComment));
    }

    /**
     * Get a list of comments for an advertisement.
     *
     * @param auth Authentication details.
     * @param adId Advertisement ID.
     * @return DTO of the list of comments.
     */
    @Override
    public Comments getComments(Authentication auth, int adId) {
        log.info("--- service started getComments");
        List<Comment> commentList = commentRepository.findCommentsByAd_Id(adId);
        return commentMapper.outDtos(commentList);
    }

    /**
     * Update a comment on an advertisement.
     *
     * @param auth        Authentication details.
     * @param adId        Advertisement ID.
     * @param commentId   Comment ID.
     * @param comCreOrUpd DTO with information to update the comment.
     * @return Updated DTO of the comment.
     */
    @Override
    public CommentDto updateComment(Authentication auth, int adId, int commentId, CommentCreateOrUpdate comCreOrUpd) {
        log.debug("--- service started updateComment");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName())
                .orElseThrow(()->new UserNotFoundException("User not found: " + auth.getName()));

        Ad ad = adRepository.findById(adId)
                .orElseThrow(()-> new EntityNotFoundException("Advertisement not found with ID: " + adId));

        Comment currentComment = commentRepository.findById(commentId)
                .orElseThrow(()->new EntityNotFoundException("Comment not found with ID: " + commentId));

        if(userEntity.equals(currentComment.getUserEntity()))
        {
            Comment newComment = commentMapper.inDto(userEntity,ad,comCreOrUpd);
            newComment.setId(currentComment.getId());
            return commentMapper.outDto(commentRepository.save(newComment));
        }
        return  null;
    }

    /**
     * Delete a comment on an advertisement.
     *
     * @param auth      Authentication details.
     * @param adId      Advertisement ID.
     * @param commentId Comment ID.
     * @return True if the comment is successfully deleted, otherwise false.
     */
    @Override
    public boolean deleteComment(Authentication auth, int adId, int commentId) {
        log.debug("--- service started deleteComment");
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new EntityNotFoundException("Comment not found with ID: " + commentId));

        Ad ad = adRepository.findById(adId)
                .orElseThrow(()-> new EntityNotFoundException("Advertisement not found with ID: " + adId));

        if(comment.getAd().equals(ad)){
           commentRepository.deleteById(commentId);
           return true;
        }
        return false;
    }
}
