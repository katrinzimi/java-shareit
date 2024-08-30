package ru.practicum.shareit.rest;

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

    @Test
    public void testItemCreate() throws Exception {
        ItemDto expected = new ItemDto(1L, "item", "description", true,
                null, null, List.of());
        Mockito.when(itemService.create(eq(1L), any())).thenReturn(expected);

        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(new ItemDto(1L, "item", "description", true,
                                LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1), List.of()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()))
                .andExpect(jsonPath("$.available").value(expected.getAvailable()))
                .andExpect(jsonPath("$.lastBooking").value(expected.getLastBooking()))
                .andExpect(jsonPath("$.nextBooking").value(expected.getLastBooking()));
    }

    @Test
    public void testItemUpdate() throws Exception {
        ItemDto expected = new ItemDto(1L, "itemUpdate", "descriptionUpdate", false,
                null, null, List.of());
        Mockito.when(itemService.update(eq(1L), any())).thenReturn(expected);

        mvc.perform(patch("/items/{itemId}", 1L)
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(new ItemDto(1L, "itemUpdate", "descriptionUpdate", false,
                                LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1), List.of()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()))
                .andExpect(jsonPath("$.available").value(expected.getAvailable()))
                .andExpect(jsonPath("$.lastBooking").value(expected.getLastBooking()))
                .andExpect(jsonPath("$.nextBooking").value(expected.getLastBooking()));
    }

    @Test
    public void testItemFindById() throws Exception {
        ItemDto expected = new ItemDto(1L, "item", "description", true,
                null, null, List.of());
        Mockito.when(itemService.findById(eq(1L))).thenReturn(expected);

        mvc.perform(get("/items/{itemId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(new ItemDto(1L, "item", "description", true,
                                LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1), List.of()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()))
                .andExpect(jsonPath("$.available").value(expected.getAvailable()))
                .andExpect(jsonPath("$.lastBooking").value(expected.getLastBooking()))
                .andExpect(jsonPath("$.nextBooking").value(expected.getLastBooking()));
    }

    @Test
    public void testItemFindAll() throws Exception {
        ItemDto expected = new ItemDto(1L, "item", "description", true,
                null, null, List.of());
        List<ItemDto> itemDtoList = List.of(expected);
        Mockito.when(itemService.findAll(eq(1L))).thenReturn(itemDtoList);

        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(new ItemDto(1L, "item", "description", true,
                                LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1), List.of()))))
                .andExpect(jsonPath("$.length()").value(itemDtoList.size()))
                .andExpect(jsonPath("$[0].id").value(expected.getId()))
                .andExpect(jsonPath("$[0].name").value(expected.getName()))
                .andExpect(jsonPath("$[0].available").value(expected.getAvailable()))
                .andExpect(jsonPath("$[0].lastBooking").value(expected.getLastBooking()))
                .andExpect(jsonPath("$[0].nextBooking").value(expected.getLastBooking()));
    }

    @Test
    public void testSearch() throws Exception {
        ItemDto expected = new ItemDto(1L, "item", "description", true,
                null, null, List.of());
        List<ItemDto> itemDtoList = List.of(expected);
        Mockito.when(itemService.search(eq("item"))).thenReturn(itemDtoList);

        mvc.perform(get("/items/search")
                        .header("X-Sharer-User-Id", 1L)
                        .param("text", "item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(new ItemDto(1L, "item", "description", true,
                                LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1), List.of()))))
                .andExpect(jsonPath("$.length()").value(itemDtoList.size()))
                .andExpect(jsonPath("$[0].id").value(expected.getId()))
                .andExpect(jsonPath("$[0].name").value(expected.getName()))
                .andExpect(jsonPath("$[0].available").value(expected.getAvailable()))
                .andExpect(jsonPath("$[0].lastBooking").value(expected.getLastBooking()))
                .andExpect(jsonPath("$[0].nextBooking").value(expected.getLastBooking()));
    }

    @Test
    public void testCommentCreate() throws Exception {
        CommentDto expected = new CommentDto(1L, "comment", "Vanya", null);
        Mockito.when(itemService.createComment(eq(1L), eq(1L), any())).thenReturn(expected);

        mvc.perform(post("/items/{itemId}/comment", 1L)
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(new CommentDto(1L, "comment", "Vanya", null))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(expected.getText()))
                .andExpect(jsonPath("$.authorName").value(expected.getAuthorName()))
                .andExpect(jsonPath("$.created").value(expected.getCreated()));
    }
}
