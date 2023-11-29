package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CommentCreateOrUpdate;
import ru.skypro.homework.service.CommentService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class CommentsController {
    private final CommentService commentService;
    /**
     * Добавление комментария к объявлению
     *
     * @param adId
     * @return
     */
    @PostMapping("/{adId}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer adId,
                                                 @RequestBody CommentCreateOrUpdate comment) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CommentDto commentDto = commentService.addComment(auth,adId,comment);
        return ResponseEntity.ok(commentDto);
    }

    /**
     * Получение комментариев объявления
     *
     * @param adId
     * @return
     */
    @GetMapping("/{adId}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable Integer adId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Comments comments = commentService.getComments(auth,adId);
        if (comments.getCount() == 0){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(comments);
        }

    }

    /**
     * Обновление комментария
     * @param adId
     * @param commentId
     * @param comment
     * @return
     */
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer adId,
                                                               @PathVariable Integer commentId,
                                                               @RequestBody CommentCreateOrUpdate comment){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(commentService.updateComment(auth,adId,commentId,comment));
    }

    /**
     * Удаление комментария
     * @param adId
     * @param commentId
     * @return
     */
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<String> delComment(@PathVariable Integer adId,
                                             @PathVariable Integer commentId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        commentService.deleteComment(auth,adId,commentId);
        return ResponseEntity.ok().build();
    }
}
