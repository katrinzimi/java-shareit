package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        if (user != null) {
            return new UserDto(
                    user.getId(),
                    user.getEmail(),
                    user.getName()

            );
        }
        return null;
    }

    public static User toUser(UserCreateDto userDto) {
        return new User(
                null,
                userDto.getEmail(),
                userDto.getName()

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
