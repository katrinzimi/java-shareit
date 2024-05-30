package ru.practicum.shareit.user;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User save(User user);

    User findById(long user);

    User updateUser(User user, long userId);

    void deleteUser(long userId);
}
