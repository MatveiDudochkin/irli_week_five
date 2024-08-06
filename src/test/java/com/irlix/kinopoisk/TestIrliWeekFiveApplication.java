package com.irlix.kinopoisk;

import org.springframework.boot.SpringApplication;

public class TestIrliWeekFiveApplication {

    public static void main(String[] args) {
        SpringApplication.from(IrliWeekFiveApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
