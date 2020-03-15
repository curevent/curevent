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
@Table(name = "templates")
public class TemplateEntity {
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
    private Timestamp time;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "repeat_time")
    private Long repeat_time;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "description")
    private String description;

  //  @Column(name = "geotag")

    @Column(name = "privacy_id")
    private Long privacyId;

    @OneToMany
    private Set<TagEntity> tags;

}
