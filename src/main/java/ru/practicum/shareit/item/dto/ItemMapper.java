package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

public class ItemMapper {
    public static ItemDto toItemDto(Item item, LocalDateTime previousBookingEnd, LocalDateTime nextBookingStart) {
        if (item != null) {
            return new ItemDto(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getAvailable(),
                    previousBookingEnd,
                    nextBookingStart
            );
        }
        return null;
    }

    public static ItemDto toItemDto(Item item) {
        return toItemDto(item, null, null);
    }


    public static Item toItem(ItemDto itemDto, User owner) {
        return new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                owner,
                null
        );
    }
}
