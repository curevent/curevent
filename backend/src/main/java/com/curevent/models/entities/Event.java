package com.curevent.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;
import springfox.documentation.service.Tags;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "owner_id")
    @NotNull
    private UUID ownerId;

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

    @Column(name = "template_id")
    private UUID templateId;

    @OneToOne
    @JoinColumn(name = "privacy_id")
    private Category privacy;

    @ManyToMany
    @JoinTable (name="event_tags",
            joinColumns=@JoinColumn (name="event_id"),
            inverseJoinColumns=@JoinColumn(name="tag_id"))
    private List<Tag> tags;

    @OneToMany
    @Immutable
    @JoinColumn(name = "event_id")
    private List<Comment> comments;
}
