package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> usersEmail = new HashSet<>();
    private long id;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        checkEmailUnique(user);
        user.setId(++id);
        users.put(user.getId(), user);
        usersEmail.add(user.getEmail());
        return user;
    }

    @Override
    public User findById(long user) {
        return users.get(user);
    }

    @Override
    public User update(User newUser) {
        User oldUser = users.get(newUser.getId());
        if (!oldUser.getEmail().equals(newUser.getEmail())) {
            checkEmailUnique(newUser);
            usersEmail.remove(oldUser.getEmail());
            usersEmail.add(newUser.getEmail());
        }
        users.put(newUser.getId(), newUser);
        return newUser;
    }


    @Override
    public void delete(long userId) {
        User user = users.remove(userId);
        if (user != null) {
            usersEmail.remove(user.getEmail());
        }
    }

    public void checkEmailUnique(User user) {
        if (usersEmail.contains(user.getEmail())) {
            throw new ConflictException("Данный email уже используется");
        }
    }
}

