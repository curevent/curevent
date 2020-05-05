package com.curevent.models.entities;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "username")
    @NotNull
    private String username;

    @Email
    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "status")
    private String status;

    @Column(name = "image")
    private String image;

    @Column(name = "password")
    private String password;

    @OneToMany
    @Immutable
    @JoinColumn(name = "owner_id")
    private List<Relationship> relationships;

    @OneToMany
    @Immutable
    @JoinColumn(name = "owner_id")
    private List<Event> events;

    @OneToMany
    @Immutable
    @JoinColumn(name = "owner_id")
    private List<Template> templates;

    @OneToMany
    @Immutable
    @JoinColumn(name = "owner_id")
    private List<Comment> comments;

    @OneToMany
    @JoinColumn(name = "owner_id")
    private List<Category> categories;

    @OneToMany
    @Immutable
    @JoinColumn(name = "owner_id")
    private List<Tag> tags;
}
