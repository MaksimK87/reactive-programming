package com.epam.reactiveprogramming.createAndGet;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.epam.reactiveprogramming.model.Sport;
import com.epam.reactiveprogramming.repository.SportRepository;

import reactor.core.publisher.Mono;

@Component
public class SportHandler {
    private final SportRepository sportRepository;

    public SportHandler(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public Mono<ServerResponse> createSport(ServerRequest request) {
        String sportName = request.pathVariable("sportname");

        return sportRepository.findByTitle(sportName)
                .log()
                .flatMap(existingSport ->
                        ServerResponse.status(HttpStatus.CONFLICT)
                                .bodyValue("Sport with name '" + sportName + "' already exists"))
                .switchIfEmpty(
                        sportRepository.save(new Sport(null, sportName))
                                .flatMap(savedSport ->
                                        ServerResponse.status(HttpStatus.CREATED)
                                                .bodyValue(savedSport))
                );
    }

    public Mono<ServerResponse> searchSports(ServerRequest request) {
        String query = request.queryParam("q").orElse("");

        return ServerResponse.ok()
                .body(sportRepository.findByTitleIgnoreCase(query), Sport.class);
    }
}
