package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserRepository userRepository;

    @Override
    public List<ItemDto> findAll(long userId) {
        return repository.findAllByOwnerId(userId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        return userRepository.findById(userId)
                .map(user -> ItemMapper.toItemDto(repository.save(ItemMapper.toItem(itemDto, user))))
                .orElseThrow(() -> new NotFoundException("Пользователя не существует"));
    }

    @Override
    public void delete(long userId, long itemId) {
        repository.findById(itemId).ifPresent(item -> {
            if (!item.getOwner().getId().equals(userId)) {
                throw new NotFoundException("Данному пользователю действие недоступно");
            }
            repository.deleteById(itemId);
        });
    }

    @Override
    public ItemDto update(long userId, ItemDto itemDto) {
        return repository.findById(itemDto.getId())
                .map(item -> {
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
                    return ItemMapper.toItemDto(repository.save(item));
                })
                .orElseThrow(() -> new NotFoundException("Вещь не найдена"));
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text.isEmpty()) {
            return Collections.emptyList();
        }
        return repository.search(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto findById(long itemId) {
        return repository.findById(itemId)
                .map(ItemMapper::toItemDto)
                .orElse(null);
    }
}