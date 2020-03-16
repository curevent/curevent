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
public class EventTagTransfer {
    private UUID id;
    private TagTransfer tag;
    private UUID eventId;
}
