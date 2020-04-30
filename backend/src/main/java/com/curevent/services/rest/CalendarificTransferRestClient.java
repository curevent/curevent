package com.curevent.services.rest;

import com.curevent.models.entities.HolidayEvent;
import com.curevent.models.transfers.rest.CalendarificTransfer;
import com.curevent.models.transfers.rest.Holiday;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
@Service
@Slf4j
public class CalendarificTransferRestClient extends CalendarificSiteRestClient<CalendarificTransfer>{

    @Override
    public Class<CalendarificTransfer> getTClass() {
        return CalendarificTransfer.class;
    }

    public List<HolidayEvent> getHolidayByDayMonthYear(LocalDate date) {

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("day", Integer.toString(date.getDayOfMonth()));
            params.add("month", Integer.toString(date.getMonthValue()));
            params.add("year", Integer.toString(date.getYear()));
        List<HolidayEvent> holidayList = new LinkedList<HolidayEvent>();
        try {
            CalendarificTransfer calendarificTransfer = get(params);
            if (calendarificTransfer == null){
                return holidayList;
            }
            for (Holiday i : calendarificTransfer.getResponse().getHolidays()){
                HolidayEvent holiday = new HolidayEvent();
                holiday.setName(i.getName());
                holiday.setDescription(i.getDescription());
                holiday.setTime(LocalDate.of(date.getYear(),date.getMonthValue(),date.getDayOfMonth()));
                holidayList.add(holiday);
            }
        } catch (Exception ex){
            throw new NullPointerException();
        }
        return holidayList;
    }
}
