package com.curevent.models.transfers;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserTransfer {

    private UUID id;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String country;
    private String city;
    private String status;
    private String image;

    private List<RelationshipTransfer> relationships;
    private List<EventTransfer> events;
    private List<TemplateTransfer> templates;
    private List<CommentTransfer> comments;
}
