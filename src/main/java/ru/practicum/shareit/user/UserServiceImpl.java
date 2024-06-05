package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserDto> getAllUsers() {
        return repository.findAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        checkEmailUnique(userDto, 0);
        return UserMapper.toUserDto(repository.save(UserMapper.toUser(userDto)));
    }

    @Override
    public UserDto updateUser(UserDto userDto, long userId) {
        User user = repository.findById(userId);
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            checkEmailUnique(userDto, userId);
            user.setEmail(userDto.getEmail());
        }
        return UserMapper.toUserDto(repository.updateUser(user));
    }

    @Override
    public UserDto getUser(long userId) {
        return UserMapper.toUserDto(repository.findById(userId));
    }

    @Override
    public void deleteUser(long userId) {
        repository.deleteUser(userId);
    }

    public void checkEmailUnique(UserDto userDto, long userId) {
        Map<String, Long> userIdsByEmail = repository.findAll().stream()
                .collect(Collectors.toMap(User::getEmail, User::getId));
        if (userIdsByEmail.containsKey(userDto.getEmail()) &&
                !userIdsByEmail.get(userDto.getEmail()).equals(userId)) {
            throw new ConflictException("");
        }
    }
}
