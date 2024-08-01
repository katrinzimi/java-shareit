package ru.practicum.shareit.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data

public class UserCreateDto {
    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private final String name;

}
