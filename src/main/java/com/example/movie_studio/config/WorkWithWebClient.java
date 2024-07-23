package com.example.movie_studio.config;


import com.example.movie_studio.actor.ActorDto;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class WorkWithWebClient {

    private WebClient webClient;

    @PostConstruct
    public void init() {
        webClient = WebClient.builder()
                .baseUrl("http://localhost:8888/api/actor")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @GetMapping("/getWithWebClient/{id}")
    public Mono<ActorDto> get(@PathVariable("id") Long id) {
        return webClient.get().uri("/getActor/" + id).retrieve().bodyToMono(ActorDto.class);
    }

    @GetMapping("/getWithWebClient")
    public Flux<ActorDto> getAllActor() {
        return webClient.get().uri("/getAllActor").retrieve().bodyToFlux(ActorDto.class);
    }
}
