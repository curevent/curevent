package com.curevent.models.transfers;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateTransfer {
    private UUID id;
    private UUID ownerId;
    private Long duration;
    private Long repeatTime;
    private Integer repeatAmount;
    private String title;
    private String description;
    private Double latitude;
    private Double longitude;

    private List<CategoryTransfer> privacy;
    private List<TagTransfer> tags;
    private List<EventTransfer> events;
}
