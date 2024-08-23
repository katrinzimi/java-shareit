package ru.practicum.shareit.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.shareit.booking.dto.BookingCreateDto;

public class BookingDateValidator implements ConstraintValidator<BookingDate, BookingCreateDto> {
    @Override
    public boolean isValid(BookingCreateDto bookingDto, ConstraintValidatorContext constraintContext) {
        return !bookingDto.getStart().equals(bookingDto.getEnd());
    }
}


