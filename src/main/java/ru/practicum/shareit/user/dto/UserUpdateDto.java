package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data

public class UserUpdateDto {
    private final Long id;
    @Email
    private final String email;
    private final String name;
}
