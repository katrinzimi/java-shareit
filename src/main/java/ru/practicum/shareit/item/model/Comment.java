package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String text;
    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false, updatable = false)
    private Item item;
    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false, updatable = false)
    private User author;
    @Column(name = "created_date")
    private LocalDateTime created;
}
