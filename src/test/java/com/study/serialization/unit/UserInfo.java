package com.study.serialization.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@ToString
public class UserInfo implements Serializable {
    private String name;
    private int age;
}
