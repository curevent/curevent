package com.curevent.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "relationships")
public class Relationship {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(name = "friend_id")
    private UUID friendId;

    @ManyToOne
    @JoinColumn(name = "privacy_id")
    private Category category;
}
