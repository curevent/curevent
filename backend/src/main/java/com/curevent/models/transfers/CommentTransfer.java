package com.curevent.models.transfers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CommentTransfer {
    private UUID id;
    private UUID eventId;
    private UUID ownerId;
    private String description;
}
