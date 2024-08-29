package ru.practicum.shareit.it;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.time.Clock;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class ItemRequestItTests {

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemRequestService requestService;
    private UserDto user;
    private ItemDto item;
    private Clock clock;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void tearDown() {
        jdbcTemplate.execute("delete from items");
        jdbcTemplate.execute("delete from requests");
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
        ItemRequestCreateDto createDto = new ItemRequestCreateDto("description_request");
        ItemRequestDto result = requestService.create(createDto, user.getId());
        assertThat(result.getDescription()).isEqualTo("description_request");
        assertThat(result.getId()).isNotZero();

    }

    @Test
    public void testFindAllByUserId() {
        ItemRequestCreateDto createDto = new ItemRequestCreateDto("description_request");
        requestService.create(createDto, user.getId());
        List<ItemRequestDto> allByUserId = requestService.findAllByUserId(user.getId());
        assertEquals("", 1, allByUserId.size());
        assertThat(allByUserId.get(0).getDescription()).isEqualTo("description_request");
    }

    @Test
    public void testFindAll() {
        ItemRequestCreateDto createDto = new ItemRequestCreateDto("description_request");
        requestService.create(createDto, user.getId());
        List<ItemRequestDto> all = requestService.findAll();
        assertEquals("", 1, all.size());
        assertThat(all.get(0).getDescription()).isEqualTo("description_request");
    }

    @Test
    public void testGetRequestById() {
        ItemRequestCreateDto createDto = new ItemRequestCreateDto("description_request");
        ItemRequestDto itemRequestDto = requestService.create(createDto, user.getId());
        ItemRequestDto request = requestService.getRequestById(itemRequestDto.getId());
        assertThat(request.getDescription()).isEqualTo("description_request");
    }

}
