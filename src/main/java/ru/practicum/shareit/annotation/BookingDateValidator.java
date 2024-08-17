package ru.practicum.shareit.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.shareit.booking.dto.BookingDto;

public class BookingDateValidator implements ConstraintValidator<BookingDate, BookingDto> {
    @Override
    public boolean isValid(BookingDto bookingDto, ConstraintValidatorContext constraintContext) {
        return !bookingDto.getStart().equals(bookingDto.getEnd());
    }
}


