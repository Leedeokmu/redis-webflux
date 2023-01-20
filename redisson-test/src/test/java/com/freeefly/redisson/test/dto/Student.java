package com.freeefly.redisson.test.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    private String name;
    private int age;
    private String city;


}
