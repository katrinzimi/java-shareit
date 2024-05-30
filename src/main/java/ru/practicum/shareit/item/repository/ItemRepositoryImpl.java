package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, List<Item>> items = new HashMap<>();
    private final Map<Long, Item> itemsById = new HashMap<>();

    @Override
    public List<Item> findByUserId(long userId) {
        return items.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public Item findItem(long item) {
        return itemsById.get(item);
    }

    @Override
    public Item updateItem(long userId, long itemId, Item newItem) {
        List<Item> userItems = items.get(userId);
        if (userItems != null && userItems.contains(itemsById.get(itemId))) {
            return itemsById.computeIfPresent(itemId, (key, item) -> {
                if (newItem.getName() != null) {
                    item.setName(newItem.getName());
                }
                if (newItem.getDescription() != null) {
                    item.setDescription(newItem.getDescription());
                }
                if (newItem.getAvailable() != null) {
                    item.setAvailable(newItem.getAvailable());
                }
                return item;
            });
        }
        return null;
    }

    @Override
    public List<Item> searchItem(Long userId, String text) {
        return items.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getAvailable() &&
                        (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                                item.getDescription().toLowerCase().contains(text.toLowerCase())))
                .collect(Collectors.toList());
    }

    @Override
    public Item save(long userId, Item item) {
        item.setId(getId());
        List<Item> userItems = items.computeIfAbsent(userId, (userId1) -> new ArrayList<>());
        userItems.add(item);
        itemsById.put(item.getId(), item);

        return item;
    }

    @Override
    public void deleteByUserIdAndItemId(long userId, long itemId) {
        if (items.containsKey(userId)) {
            List<Item> userItems = items.get(userId);
            userItems.removeIf(item -> item.getId().equals(itemId));
        }
    }

    private long getId() {
        long lastId = items.values()
                .stream()
                .flatMap(Collection::stream)
                .mapToLong(Item::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }

}