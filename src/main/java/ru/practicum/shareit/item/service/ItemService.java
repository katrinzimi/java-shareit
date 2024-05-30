package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item addNewItem(long userId, Item item);

    List<Item> getItems(long userId);

    void deleteItem(long userId, long itemId);
    Item getItem(long itemId);
    Item updateItem(long userIs,long itemId,Item item);

    List<Item> searchItem(Long userId, String text);

}