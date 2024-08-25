package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.annotation.BookingDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@BookingDate
public class BookItemRequestDto {
	private long itemId;
	@FutureOrPresent
	private LocalDateTime start;
	@Future
	private LocalDateTime end;
}
