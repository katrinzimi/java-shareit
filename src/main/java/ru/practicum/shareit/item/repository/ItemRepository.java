package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    List<Item> findByUserId(long userId);

    Item save(Item item);

    void delete(long itemId);

    Item findItem(long itemId);

    Item updateItem(Item item);

    List<Item> searchItem(String text);
}