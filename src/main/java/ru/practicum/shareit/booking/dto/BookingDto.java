package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDto {
    private final Long id;
    private final Long itemId;
    @NotNull
    @FutureOrPresent
    private final LocalDateTime start;
    @NotNull
    @Future
    private final LocalDateTime end;
    private final UserDto booker;
    private final ItemDto item;
    private final Status status;


}
