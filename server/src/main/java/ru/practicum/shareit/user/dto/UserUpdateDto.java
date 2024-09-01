package ru.practicum.shareit.user.dto;

import lombok.Data;

@Data

public class UserUpdateDto {
    private final Long id;
    private final String email;
    private final String name;
}
