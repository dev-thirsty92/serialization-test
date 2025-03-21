package com.study.serialization.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UserInfo implements Serializable {
    private String name;
    private int age;
}
