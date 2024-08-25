package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@Slf4j
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @RequestBody ItemRequestCreateDto itemRequestDto) {
        log.info("Запрос создан");
        ItemRequestDto result = itemRequestService.create(itemRequestDto, userId);
        log.info("Запрос получен: {} ", itemRequestDto);
        return result;
    }

    @GetMapping
    public List<ItemRequestDto> findById(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Создан запрос на получение списка запросов на вещи по Id владельца: {}", userId);
        List<ItemRequestDto> result = itemRequestService.findAllByUserId(userId);
        log.info("Запрос получен: {} ", result);
        return result;
    }

    @GetMapping("all")
    public List<ItemRequestDto> getAll() {
        log.info("Создан запрос на получение списка запросов на вещи");
        List<ItemRequestDto> result = itemRequestService.findAll();
        log.info("Запрос получен: {} ", result);
        return result;
    }

    @GetMapping("{requestId}")
    public ItemRequestDto getRequestById(@PathVariable long requestId) {
        log.info("Создан запрос на получение списка запросов на вещи по Id: {} ", requestId);
        ItemRequestDto result = itemRequestService.getRequestById(requestId);
        log.info("Запрос получен: {} ", result);
        return itemRequestService.getRequestById(requestId);
    }
}
