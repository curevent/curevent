package com.curevent.models.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepetitionForm {
    //day, week, month, year
    private String repetitionType;
    //через сколько дней/недель/месяцев/лет
    private Integer repetitionInterval;
    private Map<DayOfWeek, Time> repetitionDays;

    private Timestamp startTime;
    private Timestamp endTime;
}