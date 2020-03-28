package com.curevent.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "templates")
public class Template {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "owner_id")
    @NotNull
    private UUID ownerId;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "repeat_time")
    private Long repeatTime;

    @Column(name = "repeat_amount")
    private Integer repeatAmount;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "description")
    @NotNull
    private String description;

  //  @Column(name = "geotag")

    @OneToOne
    @JoinColumn(name = "privacy_id")
    private Category privacy;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "template_id")
    private List<Event> events;

    @ManyToMany
    @JoinTable (name="template_tags",
            joinColumns=@JoinColumn (name="template_id"),
            inverseJoinColumns=@JoinColumn(name="tag_id"))
    private List<Tag> tags;

}
