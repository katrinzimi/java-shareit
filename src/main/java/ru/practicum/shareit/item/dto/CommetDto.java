package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

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
