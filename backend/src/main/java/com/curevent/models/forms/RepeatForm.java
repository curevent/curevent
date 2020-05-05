package com.curevent.models.forms;

import com.curevent.models.enums.RepeatType;
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
public class RepeatForm {
    private RepeatType repeatType;
    //через сколько дней/недель/месяцев/лет
    private Integer repeatInterval;
    private Map<DayOfWeek, Time> repeatDays;

    private Timestamp startTime;
    private Timestamp endTime;
}