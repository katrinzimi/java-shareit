package ru.practicum.shareit.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data

public class UserUpdateDto {
    private final Long id;
    @Email
    private final String email;
    private final String name;
}
