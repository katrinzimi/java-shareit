package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommetDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(long userId, ItemDto itemDto);

    List<ItemDto> findAll(long userId);

    void delete(long userId, long itemId);

    ItemDto findById(long itemId);

    ItemDto update(long userId, ItemDto itemDto);

    List<ItemDto> search(String text);

    CommetDto createComment(Long userId, Long itemId, CommetDto commetDto);
}