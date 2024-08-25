package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class ItemRequestMapper {
    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest, List<Item> item) {
        return new ItemRequestDto(
                itemRequest.getId(),
                itemRequest.getDescription(),
                itemRequest.getCreated(),
                item
        );
    }

    public static ItemRequest toItemRequest(ItemRequestCreateDto itemRequestCreateDto, User user) {
        return new ItemRequest(
                null,
                itemRequestCreateDto.getDescription(),
                user,
                LocalDateTime.now()
        );
    }

}
