package ru.practicum.shareit.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.RequestClient;

@RestController
@RequestMapping(path = "/requests")
@Slf4j
@RequiredArgsConstructor
public class ItemRequestController {
    private final RequestClient client;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @RequestBody ItemRequestCreateDto itemRequestDto) {
        log.info("Запрос создан");
        return client.createItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object> findById(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Создан запрос на получение списка запросов на вещи по Id владельца: {}", userId);
        return client.getItemRequest(userId);
    }

    @GetMapping("all")
    public ResponseEntity<Object> getAll(
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Создан запрос на получение списка запросов на вещи");
        return client.getRequests(from,size);
    }

    @GetMapping("{requestId}")
    public ResponseEntity<Object> getRequestById(@PathVariable long requestId) {
        log.info("Создан запрос на получение списка запросов на вещи по Id: {} ", requestId);
        return client.getRequestById(requestId);
    }
}
