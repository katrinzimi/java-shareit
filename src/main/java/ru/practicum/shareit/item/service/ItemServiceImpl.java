package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.BookingException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<ItemDto> findAll(long userId) {
        List<Item> allItems = repository.findAllByOwnerId(userId);
        List<Long> allItemIds = allItems.stream().map(Item::getId).collect(Collectors.toList());
        List<Booking> allBookings = bookingRepository.findAllByItemIdIn(allItemIds);
        Map<Long, List<Booking>> bookingsByItem = allBookings.stream()
                .collect(Collectors.groupingBy(booking -> booking.getItem().getId()));

        return allItems.stream()
                .map(item -> {
                    LocalDateTime previousBookingEnd = null;
                    LocalDateTime nextBookingStart = null;
                    List<Booking> itemBookings = bookingsByItem.getOrDefault(item.getId(), Collections.emptyList());
                    itemBookings.sort(Comparator.comparing(Booking::getStart));
                    LocalDateTime now = LocalDateTime.now();
                    for (Booking booking : itemBookings) {
                        if (booking.getEnd().isBefore(now)) {
                            previousBookingEnd = booking.getEnd();
                        }
                        if (nextBookingStart == null && booking.getStart().isAfter(now)) {
                            nextBookingStart = booking.getStart();
                        }
                    }
                    List<Comment> comments = commentRepository.findAllCommentsByItemId(item.getId());
                    return ItemMapper.toItemDto(item, previousBookingEnd, nextBookingStart, comments);
                })
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto create(long userId, ItemCreateDto itemDto) {
        return userRepository.findById(userId)
                .map(user -> ItemMapper.toItemDto(repository.save(ItemMapper.toItem(itemDto, user))))
                .orElseThrow(() -> new NotFoundException("Пользователя не существует"));
    }

    @Override
    public void delete(long userId, long itemId) {
        repository.findById(itemId).ifPresent(item -> {
            if (!item.getOwner().getId().equals(userId)) {
                throw new NotFoundException("Данному пользователю действие недоступно");
            }
            repository.deleteById(itemId);
        });
    }

    @Override
    public ItemDto update(long userId, ItemDto itemDto) {
        Item item = repository.findById(itemDto.getId()).orElseThrow(() -> new NotFoundException("Вещь не найдена"));
        if (!item.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Данному пользователю действие недоступно");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        return ItemMapper.toItemDto(repository.save(item));
    }


    @Override
    public List<ItemDto> search(String text) {
        if (text.isEmpty()) {
            return Collections.emptyList();
        }
        return repository.search(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto createComment(long userId, long itemId, CommentCreateDto commentDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователя не существует"));
        Item item = repository.findById(itemId).orElseThrow(() -> new NotFoundException("Вещи не существует"));

        List<Booking> pastBookings = bookingRepository.findPastBookings(userId);
        if (pastBookings.isEmpty()) {
            throw new BookingException("Не возможно оставить отзыв не завершенному бронированию");
        }
        Comment comment = CommentMapper.toComment(commentDto, item, user);
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public ItemDto findById(long itemId) {
        return repository.findById(itemId)
                .map(item -> {
                    List<Comment> comments = commentRepository.findAllCommentsByItemId(item.getId());
                    return ItemMapper.toItemDto(item, null, null, comments);
                })
                .orElseThrow(() -> new NotFoundException("Вещь не найдена"));
    }
}