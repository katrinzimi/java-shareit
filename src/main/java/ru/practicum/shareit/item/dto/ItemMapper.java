package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        if (item != null) {
            return new ItemDto(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getAvailable()
            );
        }
        return null;
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
