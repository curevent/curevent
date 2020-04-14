package com.curevent.services.registry;

import com.curevent.annotations.RepeatServiceFor;
import com.curevent.models.enums.RepeatType;
import com.curevent.services.repeatServices.RepeatService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RepeatServiceRegistry {

    private static Map<RepeatType, RepeatService> servicesByType = new HashMap<>();

    static void addService(RepeatService service) {
        servicesByType.put(service.getClass().getAnnotation(RepeatServiceFor.class).type(), service);
    }

    public RepeatService getByType(RepeatType type) {
        return servicesByType.get(type);
    }
}