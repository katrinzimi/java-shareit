package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private long id;

    @Override
    public List<Item> findByUserId(long userId) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Item findItem(long item) {
        return items.get(item);
    }

    @Override
    public Item updateItem(Item newItem) {
        items.put(newItem.getId(), newItem);
        return newItem;
    }

    @Override
    public List<Item> searchItem(String text) {
        return items.values()
                .stream()
                .filter(item -> item.getAvailable() &&
                        (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                                item.getDescription().toLowerCase().contains(text.toLowerCase())))
                .collect(Collectors.toList());
    }

    @Override
    public Item save(Item item) {
        item.setId(++id);
        items.put(item.getId(), item);

        return item;
    }

    @Override
    public void delete(long itemId) {
        items.remove(itemId);
    }
}