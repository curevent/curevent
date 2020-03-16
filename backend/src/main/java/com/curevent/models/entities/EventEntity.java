package com.curevent.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
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

    @Column(name = "owner_id")
    @NotNull
    private UUID ownerId;

    @Column(name = "time")
  //  @NotNull
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

    @OneToOne
    @JoinColumn(name = "privacy_id")
    private CategoryEntity privacy;

    @OneToMany
    @JoinColumn(name = "event_id")
    private List<EventTagEntity> eventTags;

//    @OneToMany
//    private Set<CommentEntity> comments;
}
