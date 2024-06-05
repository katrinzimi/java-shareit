package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private long id;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User save(User user) {
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User findById(long user) {
        return users.get(user);
    }

    @Override
    public User updateUser(User newUser) {
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public void deleteUser(long userId) {
        users.remove(userId);
    }

}

