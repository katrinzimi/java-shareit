package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingState;

import java.util.List;

public interface BookingService {
    BookingDto create(long userId, BookingDto bookingDto);

    BookingDto approveBooking(Long userId, Long bookingId, Boolean approved);

    BookingDto findById(Long userId, Long bookingId);

    List<BookingDto> findBookingsByUser(Long userId, BookingState state);

    List<BookingDto> findBookingsByOwner(Long userId, BookingState state);
}
