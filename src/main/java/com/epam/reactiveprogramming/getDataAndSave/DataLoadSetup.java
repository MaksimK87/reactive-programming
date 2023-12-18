package com.epam.reactiveprogramming.getDataAndSave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.epam.reactiveprogramming.model.Sport;
import com.epam.reactiveprogramming.repository.SportRepository;

import reactor.core.publisher.Flux;

@Component
public class DataLoadSetup {

    @Autowired
    private SportRepository repository;

    public void loadData() {

        WebClient client = WebClient.create("https://jsonplaceholder.typicode.com");

        Flux<Sport> sportsFlux = client.get()
                .uri("/photos")
                .retrieve()
                .bodyToFlux(Sport.class)
                .log();

        repository.saveAll(sportsFlux).subscribe();

    }


}
