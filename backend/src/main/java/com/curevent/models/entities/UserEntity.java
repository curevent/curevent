package com.curevent.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
public class UserEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
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

    private String name;
    private String surname;
    private String country;
    private String city;
    private String status;
    private String role;

    @NotNull
    @Column(name = "password")
    private String password;

    @OneToMany
    @JoinColumn(name="owner_id")
    private List <Relationship> relationships;

    @OneToMany
    @JoinColumn(name = "owner_id")
    private List<Event> events;

    @OneToMany
    @JoinColumn(name = "owner_id")
    private List<Template> templates;

    @OneToMany
    @JoinColumn(name = "owner_id")
    private List<Comment> comments;
}
