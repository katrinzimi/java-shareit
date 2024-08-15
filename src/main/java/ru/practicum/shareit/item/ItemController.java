package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommetDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> findAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.findAll(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @RequestBody @Valid ItemDto itemDto) {
        return itemService.create(userId, itemDto);
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestHeader("X-Sharer-User-Id") long userId,
                       @PathVariable long itemId) {
        itemService.delete(userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto findById(@PathVariable long itemId) {
        return itemService.findById(itemId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @PathVariable long itemId,
                          @RequestBody ItemDto itemDto) {
        return itemService.update(userId,
                new ItemDto(itemId, itemDto.getName(), itemDto.getDescription(),
                        itemDto.getAvailable(), null, null, null));
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestHeader("X-Sharer-User-Id") Long userId,
                                @RequestParam String text) {
        return itemService.search(text);
    }

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public CommetDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                            @PathVariable long itemId,
                            @RequestBody CommetDto commetDto) {
        return itemService.createComment(userId, itemId, commetDto);
    }
}