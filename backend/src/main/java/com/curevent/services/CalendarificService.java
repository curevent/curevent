package com.curevent.services;

import com.curevent.models.entities.HolidayEvent;
import com.curevent.services.rest.CalendarificTransferRestClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
@Transactional
public class CalendarificService {

    @Value("${holidayCache.days_to_delete:3}")
    private int DAYS_TO_DELETE_CACHE;

    public CalendarificTransferRestClient calendarificRestClient;
    private final List<HolidayEvent> holidayListCache;

    private List<HolidayEvent> getHolidaysEntitiesByDate(List<HolidayEvent> holidayEvents, LocalDate queryTime) {
        return holidayEvents.stream()
                .filter(holidayEvent -> holidayEvent.getTime().isEqual(queryTime))
                .collect(Collectors.toList());
    }

    public List<HolidayEvent> getHolidaysByDate(LocalDate queryTime){
        List<HolidayEvent> holidayEventList = getHolidaysEntitiesByDate(holidayListCache, queryTime);
        if (holidayEventList.isEmpty()){

            // Delete old holiday in cache
            holidayListCache.removeIf(i -> i.getTime().plusDays(DAYS_TO_DELETE_CACHE).isBefore(LocalDate.now()));

            holidayEventList = getHolidaysEntitiesByDate(calendarificRestClient.getHolidayByDayMonthYear(queryTime), queryTime);
            holidayListCache.addAll(holidayEventList);
        }
        return holidayEventList;
    }

}
