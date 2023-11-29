package ru.skypro.homework.mapper;

import ru.skypro.homework.dto.CommentCreateOrUpdate;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.UserEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {
    //Вспомогательный маппер из Comment в СommentDto
    public static CommentDto outDto(Comment comment) {
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
    public static Comments outDtos(List<Comment> commentsList) {
        Comments commentsDto = new Comments();
        List<CommentDto> commentDtoList = commentsList.stream()
                .map(CommentMapper::outDto)
                .collect(Collectors.toList());
        commentsDto.setResults(commentDtoList);
        commentsDto.setCount(commentDtoList.size());
        return commentsDto;
    }

    public static Comment inDto(UserEntity userEntity, Ad ad, CommentCreateOrUpdate crOrUpd) {
        Comment comment = new Comment();
        comment.setDateTime(LocalDateTime.now());
        comment.setText(crOrUpd.getText());
        comment.setUserEntity(userEntity);
        comment.setAd(ad);
        return comment;
    }
}
