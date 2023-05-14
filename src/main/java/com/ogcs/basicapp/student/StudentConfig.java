package com.ogcs.basicapp.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student Marko = new Student(
                    "Marko",
                    "markopavlovic316@gmail.com",
                    LocalDate.of(2001, Month.FEBRUARY,9)
            );
            Student Ivan = new Student(
                    "Ivan",
                    "igalac1998@gmail.com",
                    LocalDate.of(1998, Month.APRIL,14)
            );

            repository.saveAll(
                    List.of(Marko,Ivan)
            );
        };
    }
}
