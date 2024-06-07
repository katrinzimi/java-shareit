package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private final Map<Long, Map<Long, Item>> itemsByOwner = new HashMap<>();
    private long id;

    @Override
    public List<Item> findByUserId(long userId) {
        return new ArrayList<>(itemsByOwner.getOrDefault(userId, Collections.emptyMap()).values());
    }

    @Override
    public Item findById(long item) {
        return items.get(item);
    }

    @Override
    public Item update(Item newItem) {
        items.put(newItem.getId(), newItem);
        Map<Long, Item> userItems = itemsByOwner.computeIfAbsent(newItem.getOwner().getId(), (userId1) -> new HashMap<>());
        userItems.put(newItem.getId(), newItem);
        return newItem;
    }

    @Override
    public List<Item> search(String text) {
        return items.values()
                .stream()
                .filter(item -> item.getAvailable() &&
                        (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                                item.getDescription().toLowerCase().contains(text.toLowerCase())))
                .collect(Collectors.toList());
    }

    @Override
    public Item create(Item item) {
        item.setId(++id);
        items.put(item.getId(), item);
        Map<Long, Item> userItems = itemsByOwner.computeIfAbsent(item.getOwner().getId(), (userId1) -> new HashMap<>());
        userItems.put(item.getId(), item);

        return item;
    }

    @Override
    public void delete(long itemId) {
        items.remove(itemId);
    }
}