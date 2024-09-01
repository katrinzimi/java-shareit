package ru.practicum.shareit.json;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemJsonTests {

    private final JacksonTester<ItemDto> json;
    private ItemDto expected;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @BeforeEach
    void setup() {
        expected = new ItemDto(1L, "item", "description", true,
                LocalDateTime.parse("2024-08-29T00:00:00").minusDays(2),
                LocalDateTime.parse("2024-08-29T00:00:00").plusDays(1), List.of());
    }

    @Test
    void serializeItemDto() throws Exception {
        JsonContent<ItemDto> result = json.write(expected);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("item");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isTrue();
        assertThat(result).extractingJsonPathStringValue("$.lastBooking").isEqualTo(expected.getLastBooking().format(formatter));
        assertThat(result).extractingJsonPathStringValue("$.nextBooking").isEqualTo(expected.getNextBooking().format(formatter));
    }
}
