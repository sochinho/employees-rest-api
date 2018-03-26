package com.braintri.employees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.braintri.employees")
public class EmployeesRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeesRestApiApplication.class, args);
    }
}
