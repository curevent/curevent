package com.curevent.models.transfers;

import lombok.*;

import java.sql.Timestamp;
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
    private Timestamp time;
    private Long duration;
    private Long repeat_time;
    private String title;
    private String description;

  //  @Column(name = "geotag")
    private CategoryTransfer privacy;
    private List<TagTransfer> tags;
}
