package com.curevent.services.registry;

import com.curevent.services.repeatServices.RepeatService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class RepeatServiceBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof RepeatService) {
            RepeatServiceRegistry.addService((RepeatService) bean);
        }
        return bean;
    }
}
