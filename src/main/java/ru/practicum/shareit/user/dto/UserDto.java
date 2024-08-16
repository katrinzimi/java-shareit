package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class UserDto {
    private final Long id;
    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private final String name;

}
