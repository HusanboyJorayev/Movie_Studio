package com.example.movie_studio;

import com.example.movie_studio.actor.Actor;
import com.example.movie_studio.actor.ActorRepository;
import com.example.movie_studio.casts.Casts;
import com.example.movie_studio.casts.CastsRepository;
import com.example.movie_studio.director.Director;
import com.example.movie_studio.director.DirectorRepository;
import com.example.movie_studio.movie.Movie;
import com.example.movie_studio.movie.MovieRepository;
import com.example.movie_studio.studio.Studio;
import com.example.movie_studio.studio.StudioRepository;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@SecurityScheme(name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer", bearerFormat = "JWT")
@SecurityScheme(name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic")
public class MovieStudioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieStudioApplication.class, args);
    }

    //@Bean
    // fake actors
    public CommandLineRunner runActor(ActorRepository repository) {
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

    //@Bean
    // fake casts
    public CommandLineRunner runCasts(CastsRepository repository) {
        return args -> {
            for (int i = 1; i <= 10; i++) {
                Casts actor = Casts.builder()
                        .createdAt(LocalDateTime.now())
                        .actorId((long) (1 + i / 2))
                        .movieId(1 + i / 2)
                        .roleType("ACTOR")
                        .build();

                repository.save(actor);
            }
        };
    }

    //@Bean
    // fake directors
    public CommandLineRunner runDirector(DirectorRepository repository) {
        return args -> {
            for (int i = 1; i <= 100; i++) {
                Director director = Director.builder()
                        .country("country" + i)
                        .gender("male")
                        .name("name" + i)
                        .yearBirth(1921 + i)
                        .placeBirth("place" + i)
                        .build();
                repository.save(director);
            }
        };
    }

    //@Bean
    // fake movies
    public CommandLineRunner runMovies(MovieRepository repository) {
        return args -> {
            for (int i = 1; i <= 100; i++) {
                Movie movie = Movie.builder()
                        .category("category" + i)
                        .countryOfRelease("countryOfRelease" + i)
                        .studioId(1 + i / 2)
                        .directorId(1 + i / 2)
                        .filmingLocation("filmingLocation" + i)
                        .language("language" + i)
                        .yearOfRelease(1830 + i)
                        .name("name" + i)
                        .build();
                repository.save(movie);
            }
        };
    }

    //@Bean
    // fake studios
    public CommandLineRunner runStudio(StudioRepository repository) {
        return args -> {
            for (int i = 1; i <= 100; i++) {
                Studio studio = Studio.builder()
                        .city("city" + i)
                        .companyName("company name" + i)
                        .founded(i)
                        .companyType("company type" + i)
                        .build();
                repository.save(studio);
            }
        };
    }

    @Bean
    public GroupedOpenApi actorApi() {
        return GroupedOpenApi.builder()
                .group("actor")
                .pathsToMatch("/api/actor/**")
                .build();
    }

    @Bean
    public GroupedOpenApi castsApi() {
        return GroupedOpenApi.builder()
                .group("casts")
                .pathsToMatch("/api/casts/**")
                .build();
    }

    @Bean
    public GroupedOpenApi directorApi() {
        return GroupedOpenApi.builder()
                .group("director")
                .pathsToMatch("/api/director/**")
                .build();
    }

    @Bean
    public GroupedOpenApi movieApi() {
        return GroupedOpenApi.builder()
                .group("movie")
                .pathsToMatch("/api/movie/**")
                .build();
    }

    @Bean
    public GroupedOpenApi studioApi() {
        return GroupedOpenApi.builder()
                .group("studio")
                .pathsToMatch("/api/studio/**")
                .build();
    }
}
