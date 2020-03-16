package com.curevent.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
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

    private String name;
    private String surname;
    private String country;
    private String city;
    private String status;

    @NotNull
    @Column(name = "password")
    private String password;

    @OneToMany
    @JoinColumn(name="owner_id")
    private List <RelationshipEntity> relationships;

    @OneToMany
    @JoinColumn(name = "owner_id")
    private List<EventEntity> events;

//    @OneToMany
//    @JoinColumn(name = "owner_id")
//    private Set<TemplateEntity> templates;
//
//    @OneToMany
//    private Set<CommentEntity> comments;
}
