package com.epam.reactiveprogramming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import com.epam.reactiveprogramming.backpressure.EtlProcessor;
import com.epam.reactiveprogramming.getDataAndSave.DataLoadSetup;

@EnableR2dbcRepositories
@SpringBootApplication
public class ReactiveProgrammingApplication implements ApplicationListener<ContextRefreshedEvent> {

    private DataLoadSetup dataLoadSetup;
    private EtlProcessor etlProcessor;

    public static void main(String[] args) {

        SpringApplication.run(ReactiveProgrammingApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        dataLoadSetup = event.getApplicationContext().getBean(DataLoadSetup.class);
       // dataLoadSetup.loadData();
        etlProcessor = event.getApplicationContext().getBean(EtlProcessor.class);
        etlProcessor.loadDataWithBackPressure();

    }
}
