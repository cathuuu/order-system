package com.example.authentication.compnents;

import com.example.authentication.mappers.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class TestBean {
    public TestBean(UserMapper mapper) {
        System.out.println("UserMapper bean: " + mapper);
    }
}