package com.curevent.services.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.MultiValueMap;

public abstract class CalendarificSiteRestClient<T> extends RESTClient<T> {
    @Override
    public String getPath(){
        return "/api/v2/holidays";
    }
    @Override
    public String getHost(){
        return "calendarific.com";
    }

    @Value("${calendarific.api.v2.api_key}")
    private String apiKey;
    @Value("${calendarific.api.v2.country}")
    private String country;

    public T get(MultiValueMap<String, String> params){
        params.add("api_key", apiKey);
        params.add("country", country);
        return (T) super.get(params);
    }
}
