package com.example.hello.pojo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class ExtendableBean 
{
    private Map<String, String> properties;
 
    @JsonAnySetter
    public void add(String key, String value) {
        properties.put(key, value);
    }
}
