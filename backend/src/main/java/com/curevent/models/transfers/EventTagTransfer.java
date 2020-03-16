package com.curevent.models.transfers;

import com.curevent.models.entities.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
