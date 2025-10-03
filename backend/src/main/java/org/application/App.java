package org.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;

@SpringBootApplication
@Async
public class App {

    public static void main(String[] args) {
		SpringApplication.run(App.class, args);
    }
}
