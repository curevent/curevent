package com.curevent.services.stuffservices;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class StuffServiceBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof StuffService) {
            StuffServiceRegistry.addService((StuffService) bean);
        }
        return bean;
    }

}
