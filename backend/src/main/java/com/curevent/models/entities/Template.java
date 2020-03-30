package com.curevent.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "templates")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "template_tags",
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

}
