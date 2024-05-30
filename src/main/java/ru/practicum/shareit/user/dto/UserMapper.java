package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName()

        );
    }

    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getName()

        );
    }
}
