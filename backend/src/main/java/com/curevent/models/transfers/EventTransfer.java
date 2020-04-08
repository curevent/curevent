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
public class EventTransfer {
    private UUID id;
    private UUID ownerId;
    private Timestamp time;
    private Long duration;
    private String title;
    private String description;
    private UUID templateId;
    private Double latitude;
    private Double longitude;

    private List<CategoryTransfer> privacy;
    private List<TagTransfer> tags;
    private List<CommentTransfer> comments;
    private List <UserTransfer> blackList;
}
