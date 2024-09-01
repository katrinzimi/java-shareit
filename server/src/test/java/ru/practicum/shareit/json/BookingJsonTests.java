package ru.practicum.shareit.json;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingJsonTests {

    private final JacksonTester<BookingDto> json;
    private BookingDto expected;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @BeforeEach
    void setup() {
        UserDto booker = new UserDto(2L, "email2@mail.ru", "Petya");
        ItemDto item = new ItemDto(1L, "item", "description", true, null, null, List.of());
        expected = new BookingDto(1L, LocalDateTime.parse("2024-08-29T00:00:00").minusDays(2),
                LocalDateTime.parse("2024-08-29T00:00:00").minusDays(1), booker, item, Status.WAITING);
    }

    @Test
    void serializeItemDto() throws Exception {
        JsonContent<BookingDto> result = json.write(expected);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo(expected.getStart().format(formatter));
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo(expected.getEnd().format(formatter));
        assertThat(result).extractingJsonPathNumberValue("$.booker.id").isEqualTo(2);
        assertThat(result).extractingJsonPathNumberValue("$.item.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo(String.valueOf(expected.getStatus()));
    }
}
