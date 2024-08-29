package ru.practicum.shareit.it;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentDto;
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
public class ItemItTests {

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
    public void testItemCreate() {
        assertThat(item.getName()).isEqualTo("item");
        assertThat(item.getDescription()).isEqualTo("description");
        assertThat(item.getAvailable()).isTrue();
    }

    @Test
    public void testUserUpdate() {
        ItemDto updateDto = new ItemDto(item.getId(), "item_2",
                "description_2", false, null, null, List.of());
        ItemDto update = itemService.update(user.getId(), updateDto);
        assertThat(update.getDescription()).isEqualTo("description_2");
        assertThat(update.getName()).isEqualTo("item_2");
        assertThat(update.getAvailable()).isFalse();
    }

    @Test
    public void testItemFindById() {
        ItemDto result = itemService.findById(item.getId());
        assertThat(result.getId()).isEqualTo(5L);
        assertThat(result.getName()).isEqualTo("item");
        assertThat(result.getAvailable()).isTrue();
    }

    @Test
    public void testCommentCreate() {
        UserCreateDto userCreateDto = new UserCreateDto("emailNew@mail.ru", "Roma");
        UserDto userBooker = userService.create(userCreateDto);
        BookingCreateDto createBookingDto = new BookingCreateDto(item.getId(), LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));
        bookingService.create(userBooker.getId(), createBookingDto);
        CommentCreateDto createDto = new CommentCreateDto("Comment");
        CommentDto comment = itemService.createComment(userBooker.getId(), item.getId(), createDto);
        assertThat(comment.getId()).isNotZero();
        assertThat(comment.getText()).isEqualTo("Comment");
        assertThat(comment.getAuthorName()).isEqualTo(userBooker.getName());
    }

    @Test
    public void testItemFindAll() {
        UserCreateDto userCreateDto = new UserCreateDto("emailNew@mail.ru", "Roma");
        UserDto userBooker = userService.create(userCreateDto);
        BookingCreateDto createBookingDto = new BookingCreateDto(item.getId(), LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));
        bookingService.create(userBooker.getId(), createBookingDto);
        CommentCreateDto createDto = new CommentCreateDto("Comment");
        itemService.createComment(userBooker.getId(), item.getId(), createDto);
        List<ItemDto> all = itemService.findAll(user.getId());

        assertEquals("", 1, all.size());
        assertThat(all.get(0).getName()).isEqualTo(item.getName());
        assertThat(all.get(0).getDescription()).isEqualTo(item.getDescription());
        //assertThat(all.get(0).getComments()).isEqualTo(item.getComments());
    }

    @Test
    public void testSearch() {
        List<ItemDto> search = itemService.search("item");
        assertEquals("", 1, search.size());
        assertThat(search.get(0).getName()).isEqualTo(item.getName());
        assertThat(search.get(0).getDescription()).isEqualTo(item.getDescription());
    }


}
