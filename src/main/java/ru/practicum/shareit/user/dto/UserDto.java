package ru.practicum.shareit.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data

public class UserDto {
    private final Long id;
    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private final String name;

}
