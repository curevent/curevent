package com.curevent.services.stuffservices.registry;

import com.curevent.services.stuffservices.StuffService;
import com.curevent.services.stuffservices.StuffServiceFor;
import com.curevent.services.stuffservices.Type;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StuffServiceRegistry {

    private static Map<Type, StuffService> servicesByType = new HashMap<>();

    static void addService(StuffService service) {
        servicesByType.put(service.getClass().getAnnotation(StuffServiceFor.class).type(), service);
    }

    public StuffService getByType(Type type) {
        return servicesByType.get(type);
    }

}
