package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;

@Data
@Entity
@NoArgsConstructor
@Table(name = "requests")
public class ItemRequest {
    @Id
    @Column
    private long id;
    @Column
    private String description;
    @ManyToOne(optional = false)
    @JoinColumn(name = "requestor_id", nullable = false, updatable = false)
    private User requestor;
}
