package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto create(UserCreateDto userDto);

    UserDto update(UserUpdateDto userDto, long userId);

    UserDto findById(long userId);

    void deleteUser(long userId);
}