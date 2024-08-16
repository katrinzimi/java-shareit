package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class UserCreateDto {
    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private final String name;

}
