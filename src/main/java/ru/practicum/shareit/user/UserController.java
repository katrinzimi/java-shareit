package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        log.info("Получение списка пользователей");
        List<UserDto> result = userService.findAll();
        log.info("Список получен:{}", result);
        return result;
    }

    @PostMapping
    public UserDto create(@RequestBody @Valid UserCreateDto userDto) {
        log.info("Создание пользователя: {} ", userDto);
        UserDto result = userService.create(userDto);
        log.info("Пользователь создан:{} ", result);
        return result;
    }

    @PatchMapping("/{userId}")
    public UserDto update(@RequestBody @Valid UserUpdateDto userDto,
                          @PathVariable long userId) {
        log.info("Обновение пользователя: {}", userDto);
        UserDto result = userService.update(userDto, userId);
        log.info("Пользователь обновлен: {}", result);
        return result;
    }

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable long userId) {
        log.info("Получение пользователя по id: {}", userId);
        UserDto result = userService.findById(userId);
        log.info("Пользователь получен: {}", result);
        return result;
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        log.info("Удаление пользователя по id: {}", userId);
        userService.deleteUser(userId);
        log.info("Пользователь удален");
    }
}