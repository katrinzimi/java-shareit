package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemCreateDto {
    @NotBlank
    private final String name;
    @NotBlank
    private final String description;
    @NotNull
    private final Boolean available;

}
