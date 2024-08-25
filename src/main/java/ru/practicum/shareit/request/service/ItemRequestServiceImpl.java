package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository repository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemRequestDto create(ItemRequestCreateDto itemRequestCreateDto, long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    ItemRequest save = repository.save(ItemRequestMapper.toItemRequest(itemRequestCreateDto, user));
                    return ItemRequestMapper.toItemRequestDto(save, List.of());
                })
                .orElseThrow(() -> new NotFoundException("Пользователя не существует"));
    }

    @Override
    public List<ItemRequestDto> findAllByUserId(long userId) {
        List<ItemRequest> itemRequests = repository.findAllByRequestorId(userId);
        return mapToListDto(itemRequests);
    }

    @Override
    public List<ItemRequestDto> findAll() {
        List<ItemRequest> itemRequests = repository.findAll();
        return mapToListDto(itemRequests);
    }

    private List<ItemRequestDto> mapToListDto(List<ItemRequest> itemRequests) {
        List<Long> allItemRequestsIds = itemRequests.stream()
                .map(ItemRequest::getId)
                .toList();
        List<Item> allItems = itemRepository.findAllByRequestIdIn(allItemRequestsIds);
        Map<Long, List<Item>> itemsByRequestId = allItems.stream()
                .collect(Collectors.groupingBy(item -> item.getRequest().getId()));


        return itemRequests.stream()
                .map(itemRequest -> {
                    List<Item> items = itemsByRequestId.getOrDefault(itemRequest.getRequestor().getId(), Collections.emptyList());
                    return ItemRequestMapper.toItemRequestDto(itemRequest, items);
                })
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto getRequestById(long requestId) {
        return repository.findById(requestId)
                .map(itemRequest -> {
                    List<Item> items = itemRepository.findAllByRequestIdIn(List.of(requestId));
                    return ItemRequestMapper.toItemRequestDto(itemRequest, items);
                })
                .orElse(null);
    }
}
