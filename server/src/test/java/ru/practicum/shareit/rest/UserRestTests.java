package ru.practicum.shareit.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.JsonUtil;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserRestTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    private UserDto expected;

    @BeforeEach
    void setup() {
        expected = new UserDto(1L, "email@mail.ru", "Petya");
    }

    @Test
    public void testUserCreate() throws Exception {
        Mockito.when(userService.create(any())).thenReturn(expected);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@mail.ru"))
                .andExpect(jsonPath("$.name").value("Petya"));
    }

    @Test
    public void testUserUpdate() throws Exception {
        UserDto userUpdateDto = new UserDto(1L, "newemail@mail.ru", "Vanya");
        Mockito.when(userService.update(any(), eq(1L))).thenReturn(userUpdateDto);

        mvc.perform(patch("/users/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(userUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("newemail@mail.ru"))
                .andExpect(jsonPath("$.name").value("Vanya"));
    }

    @Test
    public void testUserFindById() throws Exception {
        Mockito.when(userService.findById(eq(1L))).thenReturn(expected);

        mvc.perform(get("/users/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@mail.ru"))
                .andExpect(jsonPath("$.name").value("Petya"));
    }

    @Test
    public void testUserFindAll() throws Exception {
        List<UserDto> userDtoList = List.of(expected);
        Mockito.when(userService.findAll()).thenReturn(userDtoList);

        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(userDtoList.size()))
                .andExpect(jsonPath("$[0].id").value(expected.getId()))
                .andExpect(jsonPath("$[0].name").value(expected.getName()))
                .andExpect(jsonPath("$[0].email").value(expected.getEmail()));
    }

    @Test
    public void testUserDelete() throws Exception {
        mvc.perform(delete("/users/{userId}", 1L))
                .andExpect(status().isOk());
    }
}
