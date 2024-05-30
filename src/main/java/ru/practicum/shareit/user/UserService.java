package ru.practicum.shareit.user;

import java.util.List;

interface UserService {
    List<User> getAllUsers();

    User saveUser(User user);

    User updateUser(User user, long userId);

    User getUser(long userId);

    void deleteUser(long userId);
}