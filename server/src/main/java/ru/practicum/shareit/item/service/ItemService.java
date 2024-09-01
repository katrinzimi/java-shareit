package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(long userId, ItemCreateDto itemDto);

    List<ItemDto> findAll(long userId);

    void delete(long userId, long itemId);

    ItemDto findById(long itemId);

    ItemDto update(long userId, ItemDto itemDto);

    List<ItemDto> search(String text);

    CommentDto createComment(long userId, long itemId, CommentCreateDto commentDto);
}