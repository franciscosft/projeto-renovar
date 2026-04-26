package com.renovar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RenovarApplication implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(RenovarApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RenovarApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}