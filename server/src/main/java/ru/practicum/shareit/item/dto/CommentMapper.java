package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;


public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
            return new CommentDto(
                    comment.getId(),
                    comment.getText(),
                    comment.getAuthor().getName(),
                    comment.getCreated()
            );
    }

    public static Comment toComment(CommentCreateDto commentDto, Item item, User user) {
        return new Comment(
                null,
                commentDto.getText(),
                item,
                user,
                LocalDateTime.now()
        );
    }
}
