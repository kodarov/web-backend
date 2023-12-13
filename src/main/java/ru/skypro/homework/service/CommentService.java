package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentCreateOrUpdate;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;

public interface CommentService {
    CommentDto addComment(Authentication auth, int adId, CommentCreateOrUpdate comCrOrUpd);
    Comments getComments(Authentication auth, int adId);
    CommentDto updateComment(Authentication auth, int adId,int commentId, CommentCreateOrUpdate comCrOrUpd);
    boolean deleteComment(Authentication auth,int adId, int commentId);
}
