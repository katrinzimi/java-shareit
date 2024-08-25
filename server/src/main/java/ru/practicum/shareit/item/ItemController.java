package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> findAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Получение списка вещей по id:{} ", userId);
        List<ItemDto> result = itemService.findAll(userId);
        log.info("Список получен:{}", result);
        return result;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                          @RequestBody ItemCreateDto itemDto) {
        log.info("Создание вещи: {} ", itemDto);
        ItemDto result = itemService.create(userId, itemDto);
        log.info("Вещь создана:{} ", result);
        return result;
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestHeader("X-Sharer-User-Id") long userId,
                       @PathVariable long itemId) {
        log.info("Удаление вещи по id:{} ", itemId);
        itemService.delete(userId, itemId);
        log.info("Вещь удалена");
    }

    @GetMapping("/{itemId}")
    public ItemDto findById(@PathVariable long itemId) {
        log.info("Поучение вещи по id:{}", itemId);
        ItemDto result = itemService.findById(itemId);
        log.info("Вещь получена {}", result);
        return result;
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @PathVariable long itemId,
                          @RequestBody ItemDto itemDto) {
        log.info("Обновение вещи: {}", itemDto);
        ItemDto result = itemService.update(userId,
                new ItemDto(itemId, itemDto.getName(), itemDto.getDescription(),
                        itemDto.getAvailable(), null, null, null));
        log.info("Обновлено: {}", result);
        return result;
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestHeader("X-Sharer-User-Id") Long userId,
                                @RequestParam String text) {
        log.info("Выпонение поиска по тексту: {}", text);
        List<ItemDto> result = itemService.search(text);
        log.info("Выпонен поиск: {}", result);
        return result;
    }

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                             @PathVariable long itemId,
                             @RequestBody CommentCreateDto commentDto) {
        log.info("Создание комментария: {}", commentDto);
        CommentDto result = itemService.createComment(userId, itemId, commentDto);
        log.info("Создан комментрарий: {}", result);
        return result;
    }
}