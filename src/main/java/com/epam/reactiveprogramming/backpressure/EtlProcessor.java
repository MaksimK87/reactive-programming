package com.epam.reactiveprogramming.backpressure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.epam.reactiveprogramming.model.Sport;
import com.epam.reactiveprogramming.repository.SportRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class EtlProcessor {

    @Autowired
    private SportRepository repository;

    public void loadDataWithBackPressure() {

        WebClient client = WebClient.create("https://jsonplaceholder.typicode.com");

        Flux<Sport> sportsFlux = client.get()
                .uri("/photos")
                .retrieve()
                .bodyToFlux(Sport.class)
                .log();
        sportsFlux.limitRate(20)
                        .flatMap(this::processAndSaveItem)
                .subscribeOn(Schedulers.parallel())
                .subscribe();

    }

    private Mono<Void> processAndSaveItem(Sport sport) {

        return Mono.just(sport)
                .flatMap(item -> {
                    System.out.println("Processing item: " + item);
                    return repository.save(item);
                })
                .doOnError(Throwable::printStackTrace)
                .then();
    }
}
