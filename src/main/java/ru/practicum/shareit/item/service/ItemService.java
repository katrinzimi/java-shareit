package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto addNewItem(long userId, ItemDto itemDto);

    List<ItemDto> getItems(long userId);

    void deleteItem(long userId, long itemId);

    ItemDto getItem(long itemId);

    ItemDto updateItem(long userId, ItemDto itemDto);

    List<ItemDto> searchItem(String text);

}