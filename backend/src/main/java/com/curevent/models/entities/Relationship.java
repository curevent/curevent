package com.curevent.models.entities;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Cacheable
@Builder
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
