package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    List<Item> findByUserId(long userId);

    Item create(Item item);

    void delete(long itemId);

    Item findById(long itemId);

    Item update(Item item);

    List<Item> search(String text);
}