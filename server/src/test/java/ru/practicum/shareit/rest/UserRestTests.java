package ru.practicum.shareit.rest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.JsonUtil;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserRestTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void testUserCreate() throws Exception {
        UserDto expected = new UserDto(1L, "email@mail.ru", "Petya");
        Mockito.when(userService.create(any())).thenReturn(expected);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(new UserCreateDto("email@mail.ru", "Petya"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@mail.ru"))
                .andExpect(jsonPath("$.name").value("Petya"));
    }
    @Test
    public void testUserUpdate() throws Exception {
        UserDto userUpdateDto = new UserDto(1L, "newemail@mail.ru", "Vanya");
        Mockito.when(userService.update(any(),eq(1L))).thenReturn(userUpdateDto);

        mvc.perform(patch("/users/{userId}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(new UserUpdateDto(1L,"newemail@mail.ru", "Vanya"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("newemail@mail.ru"))
                .andExpect(jsonPath("$.name").value("Vanya"));
    }

}
