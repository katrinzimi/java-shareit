package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserDto> findAll() {
        return repository.findAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(UserCreateDto userDto) {
        return UserMapper.toUserDto(repository.create(UserMapper.toUser(userDto)));
    }

    @Override
    public UserDto update(UserUpdateDto userDto, long userId) {
        User user = repository.findById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователя не существует");
        }
        User newUser = new User(userId, user.getEmail(), user.getName());
        if (userDto.getName() != null) {
            newUser.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            newUser.setEmail(userDto.getEmail());
        }
        return UserMapper.toUserDto(repository.update(newUser));
    }

    @Override
    public UserDto findById(long userId) {
        return UserMapper.toUserDto(repository.findById(userId));
    }

    @Override
    public void deleteUser(long userId) {
        repository.delete(userId);
    }

}

