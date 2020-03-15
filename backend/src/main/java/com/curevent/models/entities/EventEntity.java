package com.curevent.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class EventEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

//    @Column(name = "owner_id")
//    @NotNull
//    private UUID ownerId;

    @ManyToOne
    private UserEntity owner;

    @Column(name = "time")
    @NotNull
    private Timestamp time;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "description")
    @NotNull
    private String description;

  //  @Column(name = "geotag")

    @Column(name = "privacy_id")
    private Long privacyId;

    @OneToMany
    private Set<TagEntity> tags;

    @OneToMany
    private Set<CommentEntity> comments;
}
