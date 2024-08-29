package ru.practicum.shareit.it;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class UserItTests {

    @Autowired
    private UserService userService;
    private User user;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void tearDown() {
        jdbcTemplate.execute("delete from users");
    }
//    @BeforeEach
//    void setUp() {
//        user = User.builder().id(1).name("user").email("user@test.ru").build();
//        user = userRepository.save(user);
//    }

    @Test
    public void testUserCreate() {
        UserCreateDto createDto = new UserCreateDto("email@mail.ru", "Vasya");
        UserDto createdUser = userService.create(createDto);
        assertThat(createdUser.getEmail()).isEqualTo("email@mail.ru");
        assertThat(createdUser.getName()).isEqualTo("Vasya");
        assertThat(createdUser.getId()).isNotNull();
    }

    @Test
    public void testUserUpdate() {
        UserCreateDto createDto = new UserCreateDto("email@mail.ru", "Vasya");
        UserDto createdUser = userService.create(createDto);
        UserUpdateDto newDto = new UserUpdateDto(createdUser.getId(), "NEWemail@mail.ru", "Petya");
        UserDto update = userService.update(newDto, createdUser.getId());
        assertThat(update.getEmail()).isEqualTo("NEWemail@mail.ru");
        assertThat(update.getName()).isEqualTo("Petya");
        assertThat(update.getId()).isNotNull();
    }

    @Test
    public void testUserDelete() {
        UserCreateDto createDto = new UserCreateDto("email@mail.ru", "Vasya");
        UserDto createdUser = userService.create(createDto);
        userService.deleteUser(createdUser.getId());

        UserDto user = userService.findById(createdUser.getId());
        assertThat(user).isNull();
    }

    @Test
    public void testUserFindById() {
        UserCreateDto createDto = new UserCreateDto("email@mail.ru", "Vasya");
        UserDto createdUser = userService.create(createDto);
        userService.findById(createdUser.getId());
        assertThat(createdUser.getId()).isNotZero();
        assertThat(createdUser.getEmail()).isEqualTo("email@mail.ru");
        assertThat(createdUser.getName()).isEqualTo("Vasya");
        assertThat(createdUser.getId()).isNotNull();
    }

    @Test
    public void testUserFindAll() {
        UserCreateDto createDto = new UserCreateDto("email@mail.ru", "Vasya");
        UserDto createdUser = userService.create(createDto);
        List<UserDto> all = userService.findAll();
        assertEquals("", 1, all.size());
        assertThat(all.get(0).getName()).isEqualTo(createdUser.getName());
        assertThat(all.get(0).getEmail()).isEqualTo(createdUser.getEmail());
    }


}
