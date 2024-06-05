package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

interface UserService {
    List<UserDto> getAllUsers();

    UserDto saveUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, long userId);

    UserDto getUser(long userId);

    void deleteUser(long userId);
}