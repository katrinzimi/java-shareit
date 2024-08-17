package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.exception.BookingException;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    //    @Override
//    public BookingDto create(long bookerId, BookingDto bookingDto) {
//        return userRepository.findById(bookerId)
//                .map(user -> itemRepository.findById(bookingDto.getItemId())
//                        .map(item -> {
//                            if (item.getOwner().getId().equals(user.getId())) {
//                                throw new BookingException("Владелец вещи не может создать запрос на бронирование своей вещи");
//                            }
//                            if (bookingDto.getStart().equals(bookingDto.getEnd())) {
//                                throw new BookingException("Время начала бронирования не может совпадать с окончанием бронирования");
//                            }
//                            if (!item.getAvailable()) {
//                                throw new ConflictException("Вещь недоступна");
//                            }
//                            Booking booking = BookingMapper.toBooking(bookingDto, item, user);
//                            return BookingMapper.toBookingDto(bookingRepository.save(booking));
//                        })
//                        .orElseThrow(() -> new NotFoundException("Вещи не существует")))
//                .orElseThrow(() -> new NotFoundException("Пользователя не существует"));
//
//    }
    @Override
    public BookingDto create(long bookerId, BookingDto bookingDto) {
        User user = userRepository.findById(bookerId).orElseThrow(() -> new NotFoundException("Пользователя не существует"));
        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow(() -> new NotFoundException("Вещи не существует"));
        if (item.getOwner().getId().equals(user.getId())) {
            throw new BookingException("Владелец вещи не может создать запрос на бронирование своей вещи");
        }
        if (!item.getAvailable()) {
            throw new ConflictException("Вещь недоступна");
        }
        Booking booking = BookingMapper.toBooking(bookingDto, item, user);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto approveBooking(Long userId, Long bookingId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Бронирования не существует"));
        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new BookingException("Пользователь не является владельцем вещи");
        }
        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        return BookingMapper.toBookingDto(bookingRepository.save(booking));

    }


    @Override
    public BookingDto findById(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Бронирования не существует"));
        if (booking.getBooker().getId().equals(userId) || booking.getItem().getOwner().getId().equals(userId)) {
            return BookingMapper.toBookingDto(booking);
        } else {
            throw new BookingException("Пользователь не является владельцем вещи или автором бронирования");
        }
    }

    @Override
    public List<BookingDto> findBookingsByUser(Long userId, BookingState state) {
        List<Booking> bookings = selectBookings(userId, state);
        return bookings.stream()
                .map(BookingMapper::toBookingDto)
                .sorted(Comparator.comparing(BookingDto::getStart).reversed())
                .collect(Collectors.toList());
    }

    private List<Booking> selectBookings(Long userId, BookingState state) {
        switch (state) {
            case ALL:
                return bookingRepository.findAllByBookerId(userId);
            case CURRENT:
                return bookingRepository.findCurrentBookings(userId);
            case PAST:
                return bookingRepository.findPastBookings(userId);
            case FUTURE:
                return bookingRepository.findFutureBookings(userId);
            case WAITING:
                return bookingRepository.findAllByBookerIdAndStatus(userId, Status.WAITING);
            case REJECTED:
                return bookingRepository.findAllByBookerIdAndStatus(userId, Status.REJECTED);
            default:
                throw new IllegalStateException("Неизвестный статус");
        }
    }

    @Override
    public List<BookingDto> findBookingsByOwner(Long userId, BookingState state) {
        List<Long> itemIds = itemRepository.findAllByOwnerId(userId)
                .stream().map(Item::getId).collect(Collectors.toList());
        if (itemIds.isEmpty()) {
            throw new NotFoundException("У данного пользователя нет вещей");
        }
        List<Booking> bookings = selectBookingsByItems(itemIds, state);
        return bookings.stream()
                .map(BookingMapper::toBookingDto)
                .sorted(Comparator.comparing(BookingDto::getStart).reversed())
                .collect(Collectors.toList());
    }

    private List<Booking> selectBookingsByItems(List<Long> itemIds, BookingState state) {
        switch (state) {
            case ALL:
                return bookingRepository.findAllByItemIdIn(itemIds);
            case CURRENT:
                return bookingRepository.findCurrentBookingsByItemId(itemIds);
            case PAST:
                return bookingRepository.findPastBookingsByItemId(itemIds);
            case FUTURE:
                return bookingRepository.findFutureBookingsByItemId(itemIds);
            case WAITING:
                return bookingRepository.findAllByItemIdInAndStatus(itemIds, Status.WAITING);
            case REJECTED:
                return bookingRepository.findAllByItemIdInAndStatus(itemIds, Status.REJECTED);
            default:
                throw new IllegalStateException("Неизвестный статус");
        }
    }
}
