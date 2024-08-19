package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentCreateDto {
    private final Long id;
    @NotBlank
    private final String text;
    private final LocalDateTime created;
}
