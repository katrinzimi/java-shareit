package ru.practicum.shareit.it;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class BookingItTests {

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;
    private UserDto user;
    private ItemDto item;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void tearDown() {
        jdbcTemplate.execute("delete from bookings");
        jdbcTemplate.execute("delete from comments");
        jdbcTemplate.execute("delete from items");
        jdbcTemplate.execute("delete from users");
    }

    @BeforeEach
    void setUp() {
        UserCreateDto userCreateDto = new UserCreateDto("email@mail.ru", "Vasya");
        user = userService.create(userCreateDto);
        ItemCreateDto createDto = new ItemCreateDto("item", "description", true, null);
        item = itemService.create(user.getId(), createDto);
    }

    @Test
    public void testBookingCreate() {
        UserCreateDto userCreateDto = new UserCreateDto("emailNew@mail.ru", "Roma");
        UserDto userBooker = userService.create(userCreateDto);
        BookingCreateDto createBookingDto = new BookingCreateDto(item.getId(), LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        BookingDto bookingDto = bookingService.create(userBooker.getId(), createBookingDto);
        assertThat(bookingDto.getItem().getName()).isEqualTo("item");
        assertThat(bookingDto.getItem().getDescription()).isEqualTo("description");
        assertThat(bookingDto.getBooker().getId()).isEqualTo(userBooker.getId());
        assertThat(bookingDto.getItem().getAvailable()).isTrue();
    }

    @Test
    public void testApproveBooking() {
        UserCreateDto userCreateDto = new UserCreateDto("emailNew@mail.ru", "Roma");
        UserDto userBooker = userService.create(userCreateDto);
        BookingCreateDto createBookingDto = new BookingCreateDto(item.getId(), LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        BookingDto bookingDto = bookingService.create(userBooker.getId(), createBookingDto);
        BookingDto result = bookingService.approveBooking(user.getId(), bookingDto.getId(), item.getAvailable());
        assertThat(result.getStatus()).isEqualTo(Status.APPROVED);
    }

    @Test
    public void testBookingFindById() {
        UserCreateDto userCreateDto = new UserCreateDto("emailNew@mail.ru", "Roma");
        UserDto userBooker = userService.create(userCreateDto);
        BookingCreateDto createBookingDto = new BookingCreateDto(item.getId(), LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        BookingDto bookingDto = bookingService.create(userBooker.getId(), createBookingDto);
        BookingDto result = bookingService.findById(userBooker.getId(), bookingDto.getId());
        assertThat(result.getItem().getName()).isEqualTo("item");
        assertThat(result.getItem().getDescription()).isEqualTo("description");
    }

    @Test
    public void testFindBookingsByUser() {
        UserCreateDto userCreateDto = new UserCreateDto("emailNew@mail.ru", "Roma");
        UserDto userBooker = userService.create(userCreateDto);
        BookingCreateDto createBookingDto = new BookingCreateDto(item.getId(), LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        BookingDto bookingDto = bookingService.create(userBooker.getId(), createBookingDto);
        List<BookingDto> bookingsByUser = bookingService.findBookingsByUser(userBooker.getId(), BookingState.ALL);
        assertEquals("", 1, bookingsByUser.size());
        assertThat(bookingsByUser.get(0).getItem().getName()).isEqualTo(item.getName());
        assertThat(bookingsByUser.get(0).getItem().getDescription()).isEqualTo(item.getDescription());
        assertThat(bookingsByUser.get(0).getStatus()).isEqualTo(Status.WAITING);
    }

    @Test
    public void testFindBookingsByOwner() {
        UserCreateDto userCreateDto = new UserCreateDto("emailNew@mail.ru", "Roma");
        UserDto userBooker = userService.create(userCreateDto);
        BookingCreateDto createBookingDto = new BookingCreateDto(item.getId(), LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        BookingDto bookingDto = bookingService.create(userBooker.getId(), createBookingDto);
        List<BookingDto> bookingsByUser = bookingService.findBookingsByOwner(user.getId(), BookingState.ALL);
        assertEquals("", 1, bookingsByUser.size());
        assertThat(bookingsByUser.get(0).getItem().getName()).isEqualTo(item.getName());
        assertThat(bookingsByUser.get(0).getItem().getDescription()).isEqualTo(item.getDescription());
        assertThat(bookingsByUser.get(0).getStatus()).isEqualTo(Status.WAITING);
    }


}
