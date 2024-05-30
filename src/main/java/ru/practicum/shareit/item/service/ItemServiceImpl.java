package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserRepository userRepository;

    @Override
    public List<Item> getItems(long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Item addNewItem(long userId, Item item) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователя не существует");
        }
        return repository.save(userId, item);
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        repository.deleteByUserIdAndItemId(userId, itemId);
    }

    @Override
    public Item updateItem(long userId, long itemId, Item item) {
        List<Item> userItems = repository.findByUserId(userId);
        if (userItems.stream().filter(item1 -> item1.getId().equals(itemId)).findAny().isEmpty()) {
            throw new NotFoundException("Данному пользователю действие недоступно");
        }
        return repository.updateItem(userId, itemId, item);
    }

    @Override
    public List<Item> searchItem(Long userId, String text) {
        if (text.isEmpty()) {
            return Collections.emptyList();
        }
        return repository.searchItem(userId, text);
    }

    @Override
    public Item getItem(long itemId) {
        return repository.findItem(itemId);
    }
}