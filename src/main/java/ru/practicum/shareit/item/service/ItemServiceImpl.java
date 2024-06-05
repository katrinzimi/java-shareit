package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserRepository userRepository;

    @Override
    public List<ItemDto> getItems(long userId) {
        return repository.findByUserId(userId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto addNewItem(long userId, ItemDto itemDto) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователя не существует");
        }
        return ItemMapper.toItemDto(repository.save(ItemMapper.toItem(itemDto, user)));
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        Item item = repository.findItem(itemId);
        if (!item.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Данному пользователю действие недоступно");
        }
        repository.delete(itemId);
    }

    @Override
    public ItemDto updateItem(long userId, ItemDto itemDto) {
        Item item = repository.findItem(itemDto.getId());
        if (!item.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Данному пользователю действие недоступно");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }

        return ItemMapper.toItemDto(repository.updateItem(item));
    }

    @Override
    public List<ItemDto> searchItem(String text) {
        if (text.isEmpty()) {
            return Collections.emptyList();
        }
        return repository.searchItem(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getItem(long itemId) {
        return ItemMapper.toItemDto(repository.findItem(itemId));
    }
}