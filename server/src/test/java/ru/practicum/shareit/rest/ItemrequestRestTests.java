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
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
public class ItemrequestRestTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemRequestService requestService;
    private ItemRequestDto expected;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");

    @BeforeEach
    void setup() {
        expected = new ItemRequestDto(1L, "description", LocalDateTime.now(Clock.systemDefaultZone()), List.of());
    }

    @Test
    public void testRequestCreate() throws Exception {
        Mockito.when(requestService.create(any(), eq(1L))).thenReturn(expected);

        mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()))
                .andExpect(jsonPath("$.created").value(expected.getCreated().format(formatter)));
    }


    @Test
    public void testRequestFindById() throws Exception {
        List<ItemRequestDto> requestDtoList = List.of(expected);
        Mockito.when(requestService.findAllByUserId(eq(1L))).thenReturn(requestDtoList);

        mvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(requestDtoList.size()))
                .andExpect(jsonPath("$[0].id").value(expected.getId()))
                .andExpect(jsonPath("$[0].description").value(expected.getDescription()))
                .andExpect(jsonPath("$[0].created").value(expected.getCreated().format(formatter)));
    }

    @Test
    public void testRequestFindAll() throws Exception {
        List<ItemRequestDto> requestDtoList = List.of(expected);
        Mockito.when(requestService.findAll()).thenReturn(requestDtoList);

        mvc.perform(get("/requests/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(requestDtoList.size()))
                .andExpect(jsonPath("$[0].id").value(expected.getId()))
                .andExpect(jsonPath("$[0].description").value(expected.getDescription()))
                .andExpect(jsonPath("$[0].created").value(expected.getCreated().format(formatter)));
    }

    @Test
    public void testGetRequestById() throws Exception {
        Mockito.when(requestService.getRequestById(eq(1L))).thenReturn(expected);

        mvc.perform(get("/requests/{requestId}", 1L)
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()))
                .andExpect(jsonPath("$.created").value(expected.getCreated().format(formatter)));
    }
}
