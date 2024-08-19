package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class ItemMapper {
    public static ItemDto toItemDto(Item item, LocalDateTime previousBookingEnd, LocalDateTime nextBookingStart,
                                    List<Comment> comments) {
        if (item != null) {
            return new ItemDto(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getAvailable(),
                    previousBookingEnd,
                    nextBookingStart,
                    comments
            );
        }
        return null;
    }

    public static ItemDto toItemDto(Item item) {
        return toItemDto(item, null, null, null);
    }


    public static Item toItem(ItemCreateDto itemDto, User owner) {
        return new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                owner
        );
    }
}
