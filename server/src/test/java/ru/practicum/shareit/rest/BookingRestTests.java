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
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
public class BookingRestTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookingService bookingService;
    private UserDto owner;
    private UserDto booker;
    private ItemDto item;
    private BookingDto expected;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @BeforeEach
    void setup() {
        owner = new UserDto(1L, "email@mail.ru", "Vanya");
        booker = new UserDto(2L, "email2@mail.ru", "Petya");
        item = new ItemDto(1L, "item", "description", true, null, null, List.of());
        expected = new BookingDto(1L, LocalDateTime.parse("2024-08-29T00:00:00").minusDays(2),
                LocalDateTime.parse("2024-08-29T00:00:00").minusDays(1), booker, item, Status.WAITING);
    }

    @Test
    public void testBookingCreate() throws Exception {
        Mockito.when(bookingService.create(eq(1L), any())).thenReturn(expected);

        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.start").value(expected.getStart().format(formatter)))
                .andExpect(jsonPath("$.end").value(expected.getEnd().format(formatter)))
                .andExpect(jsonPath("$.status").value(String.valueOf(expected.getStatus())));
    }

    @Test
    public void testApproveBookingUpdate() throws Exception {
        Mockito.when(bookingService.approveBooking(eq(1L), eq(2L), any())).thenReturn(expected);

        mvc.perform(patch("/bookings/{bookingId}", 2L)
                        .header("X-Sharer-User-Id", 1L)
                        .param("approved", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.start").value(expected.getStart().format(formatter)))
                .andExpect(jsonPath("$.end").value(expected.getEnd().format(formatter)))
                .andExpect(jsonPath("$.status").value(String.valueOf(expected.getStatus())));
    }

    @Test
    public void testItemFindById() throws Exception {
        Mockito.when(bookingService.findById(eq(1L), eq(1L))).thenReturn(expected);

        mvc.perform(get("/bookings/{bookingId}", 1L)
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.start").value(expected.getStart().format(formatter)))
                .andExpect(jsonPath("$.end").value(expected.getEnd().format(formatter)))
                .andExpect(jsonPath("$.status").value(String.valueOf(expected.getStatus())));
    }

    @Test
    public void testFindByUserId() throws Exception {
        List<BookingDto> bookingDtoList = List.of(expected);
        Mockito.when(bookingService.findBookingsByUser(eq(2L), any())).thenReturn(bookingDtoList);

        mvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 2L)
                        .param("state", String.valueOf(BookingState.ALL))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(bookingDtoList.size()))
                .andExpect(jsonPath("$[0].id").value(expected.getId()))
                .andExpect(jsonPath("$[0].start").value(expected.getStart().format(formatter)))
                .andExpect(jsonPath("$[0].end").value(expected.getEnd().format(formatter)))
                .andExpect(jsonPath("$[0].status").value(String.valueOf(expected.getStatus())));
    }

    @Test
    public void testFindByOwnerId() throws Exception {
        List<BookingDto> bookingDtoList = List.of(expected);
        Mockito.when(bookingService.findBookingsByOwner(eq(1L), any())).thenReturn(bookingDtoList);

        mvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1L)
                        .param("state", String.valueOf(BookingState.ALL))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(bookingDtoList.size()))
                .andExpect(jsonPath("$[0].id").value(expected.getId()))
                .andExpect(jsonPath("$[0].start").value(expected.getStart().format(formatter)))
                .andExpect(jsonPath("$[0].end").value(expected.getEnd().format(formatter)))
                .andExpect(jsonPath("$[0].status").value(String.valueOf(expected.getStatus())));
    }
}
