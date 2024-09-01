package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingState;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BookingDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                             @RequestBody BookingCreateDto bookingDto) {
        log.info("Создание бронирования: {} ", bookingDto);
        BookingDto result = bookingService.create(userId, bookingDto);
        log.info("Бронирование создано: {} ", result);
        return result;
    }

    @PatchMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingDto approveBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                     @PathVariable Long bookingId,
                                     @RequestParam(required = false, defaultValue = "false") Boolean approved) {
        log.info("Подтверждение бронирования по id бронирования: {} ", bookingId);
        BookingDto result = bookingService.approveBooking(userId, bookingId, approved);
        log.info("Бронирование подтверждено: {} ", result);
        return result;
    }

    @GetMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingDto findById(@RequestHeader("X-Sharer-User-Id") Long userId,
                               @PathVariable Long bookingId) {
        log.info("Получение бронирования по id: {} ", bookingId);
        BookingDto result = bookingService.findById(userId, bookingId);
        log.info("Бронирование получено: {} ", result);
        return result;
    }

    @GetMapping
    public List<BookingDto> findByUserId(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestParam(required = false, defaultValue = "ALL") BookingState state) {
        log.info("Получение списка бронирований по id пользователя: {}", userId);
        List<BookingDto> result = bookingService.findBookingsByUser(userId, state);
        log.info("Список бронирований получен: {} ", result);
        return result;
    }

    @GetMapping("/owner")
    public List<BookingDto> findByOwnerId(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @RequestParam(required = false, defaultValue = "ALL") BookingState state) {
        log.info("Получение списка бронирований по id владельца вещи: {}", userId);
        List<BookingDto> result = bookingService.findBookingsByOwner(userId, state);
        log.info("Список бронирований получен: {} ", result);
        return result;
    }
}
