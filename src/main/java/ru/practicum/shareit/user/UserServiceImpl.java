package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public User updateUser(User user, long userId) {
        return repository.updateUser(user, userId);
    }

    @Override
    public User getUser(long userId) {
        return repository.findById(userId);
    }

    @Override
    public void deleteUser(long userId) {
        repository.deleteUser(userId);
    }
}
