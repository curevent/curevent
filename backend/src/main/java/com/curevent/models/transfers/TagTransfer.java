package com.curevent.models.transfers;

import com.curevent.models.entities.EventTagEntity;
import com.curevent.models.entities.TemplateTagEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagTransfer {
    private UUID id;
    private String description;
}
