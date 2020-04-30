package com.curevent.models.entities;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HolidayEvent {
    private LocalDate time;
    private String name;
    private String description;
}
