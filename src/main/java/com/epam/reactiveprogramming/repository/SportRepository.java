package com.epam.reactiveprogramming.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.epam.reactiveprogramming.model.Sport;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SportRepository extends ReactiveCrudRepository<Sport, Integer> {

    Mono<Sport> findByTitle(String name);

    Flux<Sport> findByTitleIgnoreCase(String name);
}
