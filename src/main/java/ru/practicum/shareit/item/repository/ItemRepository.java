package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    List<Item> findByUserId(long userId);

    Item save(long userId,Item item);

    void deleteByUserIdAndItemId(long userId, long itemId);

    Item findItem(long item);

    Item updateItem(long userId, long itemId,Item item);

    List<Item> searchItem(Long userId, String text);
}