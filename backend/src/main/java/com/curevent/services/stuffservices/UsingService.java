package com.curevent.services.stuffservices;

import com.curevent.services.stuffservices.registry.StuffServiceRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsingService {

    private final StuffServiceRegistry registry;

    public void someLogic(Type type) {
        final StuffService stuffService = registry.getByType(type);
        stuffService.foo();
    }

}
