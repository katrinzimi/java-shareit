package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ConflictException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        checkEmailUnique(user, 0);
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User findById(long user) {
        return users.get(user);
    }

    @Override
    public User updateUser(User newUser, long userId) {
        return users.computeIfPresent(userId, (key, user) -> {
            if (newUser.getName() != null) {
                user.setName(newUser.getName());
            }
            if (newUser.getEmail() != null) {
                checkEmailUnique(newUser, userId);
                user.setEmail(newUser.getEmail());
            }
            return user;
        });
    }

    @Override
    public void deleteUser(long userId) {
        users.remove(userId);
    }

    public void checkEmailUnique(User user, long userId) {
        Map<String, Long> userIdsByEmail = users.values().stream()
                .collect(Collectors.toMap(User::getEmail, User::getId));
        if (userIdsByEmail.containsKey(user.getEmail()) &&
                !userIdsByEmail.get(user.getEmail()).equals(userId)) {
            throw new ConflictException("");
        }
    }

}

