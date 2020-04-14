package com.curevent.models.entities;

import com.curevent.models.enums.RepeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Repeat {
    private RepeatType repeatType;
    private Integer repeatInterval;
    private Map<DayOfWeek, Time> repeatDays;

    @NotNull
    private Timestamp startTime;
    private Timestamp endTime;

    private Integer yearCount;
    private Integer monthCount;
    private Integer weekCount;
    private Integer dayCount;
}
