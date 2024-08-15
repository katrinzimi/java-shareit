package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.item.model.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemDto {
    private final Long id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String description;
    @NotNull
    private final Boolean available;
    private final LocalDateTime lastBooking;
    private final LocalDateTime nextBooking;
    private final List<Comment> comments;
}
