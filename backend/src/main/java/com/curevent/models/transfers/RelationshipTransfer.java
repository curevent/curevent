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

public class RelationshipTransfer {
    private UUID id;
    private UUID ownerId;
    private UUID friendId;
    private CategoryTransfer category;
}
