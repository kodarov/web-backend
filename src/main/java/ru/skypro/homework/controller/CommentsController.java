package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CommentCreateOrUpdate;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class CommentsController {
    /**
     * Добавление комментария к объявлению
     *
     * @param id
     * @return
     */
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id,
                                                 @RequestBody CommentCreateOrUpdate comment) {
        return ResponseEntity.ok(new CommentDto());
    }

    /**
     * Получение комментариев объявления
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable Integer id) {
        return ResponseEntity.ok(new Comments());
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
        return ResponseEntity.ok(new CommentDto());
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
        return ResponseEntity.ok().build();
    }

}
