package ru.practicum.shareit.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;

public class BookingDateValidator implements ConstraintValidator<BookingDate, BookItemRequestDto> {
    @Override
    public boolean isValid(BookItemRequestDto bookingDto, ConstraintValidatorContext constraintContext) {
        return !bookingDto.getStart().equals(bookingDto.getEnd());
    }
}


