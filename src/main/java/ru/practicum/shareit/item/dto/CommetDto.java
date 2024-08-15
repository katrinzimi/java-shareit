package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommetDto {
    private final Long id;
    @NotBlank
    private final String text;
    private final Item item;
    private final String authorName;
    private final LocalDateTime created;
}
