package ru.skypro.homework.mapper;

import ru.skypro.homework.dto.CommentCreateOrUpdate;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.entity.Comment;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {
    //Вспомогательный маппер из Comment в СommentDto
    public static CommentDto CommentToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setAuthor(comment.getUserEntity().getId());
        commentDto.setAuthorImage(String.format("/users/me/image/%d",comment.getUserEntity().getAvatar().getId()));
        commentDto.setAuthorFirstName(comment.getUserEntity().getFirstName());
        commentDto.setCreatedAt(comment.getDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        commentDto.setPk(comment.getId());
        commentDto.setText(comment.getText());
        return commentDto;
    }

    //проходимся по листу Comment и маппим каждый
    public static Comments outDto(List<Comment> commentsList) {
        Comments commentsDto = new Comments();

        List<CommentDto> commentDtoList = commentsList.stream()
                .map(CommentMapper::CommentToDto)
                .collect(Collectors.toList());
        commentsDto.setResults(commentDtoList);
        return commentsDto;
    }

    public static Comment inDto(CommentCreateOrUpdate CrOrUpdComment) {
        Comment comment = new Comment();

        comment.setText(CrOrUpdComment.getText());
        return comment;
    }

}
