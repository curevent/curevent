package com.curevent.services.stuffservices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@StuffServiceFor(type = Type.B)
public class StuffServiceB implements StuffService{

    @Override
    public void foo() {
        log.debug("I am B");
    }

}
