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
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
public class ItemRestTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService itemService;
    private ItemDto expected;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @BeforeEach
    void setup() {
        expected = new ItemDto(1L, "item", "description", true,
                LocalDateTime.parse("2024-08-29T00:00:00").minusDays(2),
                LocalDateTime.parse("2024-08-29T00:00:00").plusDays(1), List.of());
    }

    @Test
    public void testItemCreate() throws Exception {
        Mockito.when(itemService.create(eq(1L), any())).thenReturn(expected);

        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()))
                .andExpect(jsonPath("$.available").value(expected.getAvailable()))
                .andExpect(jsonPath("$.lastBooking").value(expected.getLastBooking().format(formatter)))
                .andExpect(jsonPath("$.nextBooking").value(expected.getNextBooking().format(formatter)));
    }

    @Test
    public void testItemUpdate() throws Exception {
        Mockito.when(itemService.update(eq(1L), any())).thenReturn(expected);

        mvc.perform(patch("/items/{itemId}", 1L)
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()))
                .andExpect(jsonPath("$.available").value(expected.getAvailable()))
                .andExpect(jsonPath("$.lastBooking").value(expected.getLastBooking().format(formatter)))
                .andExpect(jsonPath("$.nextBooking").value(expected.getNextBooking().format(formatter)));
    }

    @Test
    public void testItemFindById() throws Exception {
        Mockito.when(itemService.findById(eq(1L))).thenReturn(expected);

        mvc.perform(get("/items/{itemId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()))
                .andExpect(jsonPath("$.available").value(expected.getAvailable()))
                .andExpect(jsonPath("$.lastBooking").value(expected.getLastBooking().format(formatter)))
                .andExpect(jsonPath("$.nextBooking").value(expected.getNextBooking().format(formatter)));
    }

    @Test
    public void testItemFindAll() throws Exception {
        List<ItemDto> itemDtoList = List.of(expected);
        Mockito.when(itemService.findAll(eq(1L))).thenReturn(itemDtoList);

        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(jsonPath("$.length()").value(itemDtoList.size()))
                .andExpect(jsonPath("$[0].id").value(expected.getId()))
                .andExpect(jsonPath("$[0].name").value(expected.getName()))
                .andExpect(jsonPath("$[0].available").value(expected.getAvailable()))
                .andExpect(jsonPath("$[0].lastBooking").value(expected.getLastBooking().format(formatter)))
                .andExpect(jsonPath("$[0].nextBooking").value(expected.getNextBooking().format(formatter)));
    }

    @Test
    public void testSearch() throws Exception {
        List<ItemDto> itemDtoList = List.of(expected);
        Mockito.when(itemService.search(eq("item"))).thenReturn(itemDtoList);

        mvc.perform(get("/items/search")
                        .header("X-Sharer-User-Id", 1L)
                        .param("text", "item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(jsonPath("$.length()").value(itemDtoList.size()))
                .andExpect(jsonPath("$[0].id").value(expected.getId()))
                .andExpect(jsonPath("$[0].name").value(expected.getName()))
                .andExpect(jsonPath("$[0].available").value(expected.getAvailable()))
                .andExpect(jsonPath("$[0].lastBooking").value(expected.getLastBooking().format(formatter)))
                .andExpect(jsonPath("$[0].nextBooking").value(expected.getNextBooking().format(formatter)));
    }

    @Test
    public void testCommentCreate() throws Exception {
        CommentDto commentDto = new CommentDto(1L, "comment", "Vanya", LocalDateTime.parse("2024-08-29T00:00:00"));
        Mockito.when(itemService.createComment(eq(1L), eq(1L), any())).thenReturn(commentDto);

        mvc.perform(post("/items/{itemId}/comment", 1L)
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(commentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(commentDto.getText()))
                .andExpect(jsonPath("$.authorName").value(commentDto.getAuthorName()))
                .andExpect(jsonPath("$.created").value(commentDto.getCreated().format(formatter)));
    }
}
