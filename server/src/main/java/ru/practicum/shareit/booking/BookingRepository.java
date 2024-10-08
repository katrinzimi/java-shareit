package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookerId(Long userId);

    List<Booking> findAllByBookerIdAndStatus(Long userId, Status status);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.end > current_timestamp " +
            "and current_timestamp > b.start " +
            "and b.booker.id = ?1")
    List<Booking> findCurrentBookings(Long userId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.end < current_timestamp " +
            "and b.booker.id = ?1")
    List<Booking> findPastBookings(Long userId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.start > current_timestamp " +
            "and b.booker.id = ?1")
    List<Booking> findFutureBookings(Long userId);

    List<Booking> findAllByItemIdIn(List<Long> itemIds);

    List<Booking> findAllByItemIdInAndStatusOrderByStart(List<Long> itemIds, Status status);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.end > current_timestamp " +
            "and current_timestamp > b.start " +
            "and b.item.id in (?1)")
    List<Booking> findCurrentBookingsByItemId(List<Long> itemIds);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.end < current_timestamp " +
            "and b.item.id in (?1)")
    List<Booking> findPastBookingsByItemId(List<Long> itemIds);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.start > current_timestamp " +
            "and b.item.id in (?1)")
    List<Booking> findFutureBookingsByItemId(List<Long> itemIds);

}
