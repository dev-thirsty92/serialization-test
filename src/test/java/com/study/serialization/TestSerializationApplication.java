package com.study.serialization;

import org.springframework.boot.SpringApplication;

public class TestSerializationApplication {

    public static void main(String[] args) {
        SpringApplication.from(SerializationApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
