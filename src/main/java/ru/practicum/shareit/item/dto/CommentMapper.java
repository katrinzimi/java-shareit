package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;


public class CommentMapper {
    public static CommetDto toCommentDto(Comment comment) {
        if (comment != null) {
            return new CommetDto(
                    comment.getId(),
                    comment.getText(),
                    comment.getItem(),
                    comment.getAuthor().getName(),
                    comment.getCreated()
            );
        }
        return null;
    }

    public static Comment toComment(CommetDto commetDto, Item item, User user) {
        return new Comment(
                commetDto.getId(),
                commetDto.getText(),
                item,
                user,
                commetDto.getCreated()
        );
    }
}
