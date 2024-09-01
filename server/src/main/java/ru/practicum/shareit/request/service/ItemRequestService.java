package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto create(ItemRequestCreateDto itemRequestDto, long userId);

    List<ItemRequestDto> findAllByUserId(long userId);

    List<ItemRequestDto> findAll();

    ItemRequestDto getRequestById(long requestId);

}
