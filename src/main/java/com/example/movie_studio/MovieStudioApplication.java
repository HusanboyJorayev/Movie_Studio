package com.example.movie_studio;

import com.example.movie_studio.actor.Actor;
import com.example.movie_studio.actor.ActorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class MovieStudioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieStudioApplication.class, args);
    }

    //@Bean
    public CommandLineRunner runner(ActorRepository repository) {
        return args -> {
            for (int i = 1; i <= 100; i++) {
                Actor actor = Actor.builder()
                        .createdAt(LocalDateTime.now())
                        .gender("Male" + i)
                        .codes(i)
                        .name("name" + i)
                        .nationality("national" + i)
                        .yearOfBirth(1920 + i)
                        .build();

                repository.save(actor);
            }
        };
    }
}
