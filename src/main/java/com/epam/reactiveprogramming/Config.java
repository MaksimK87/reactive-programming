package com.epam.reactiveprogramming;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.epam.reactiveprogramming.createAndGet.SportHandler;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import static io.r2dbc.spi.ConnectionFactoryOptions.DATABASE;
import static io.r2dbc.spi.ConnectionFactoryOptions.DRIVER;
import static io.r2dbc.spi.ConnectionFactoryOptions.HOST;
import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.PORT;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

@Configuration
public class Config extends AbstractR2dbcConfiguration {

    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {

        return new R2dbcEntityTemplate(connectionFactory);
    }

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {

        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(DRIVER, "postgresql")
                .option(HOST, "localhost")
                .option(PORT, 5432)
                .option(USER, "postgres")
                .option(PASSWORD, "admin")
                .option(DATABASE, "sports_db")
                .build();
        return ConnectionFactories.get(options);
    }

    @Bean
    public RouterFunction<ServerResponse> sportRoutes(SportHandler sportHandler) {
        return RouterFunctions.route()
                .POST("/api/v1/sport/{sportname}", sportHandler::createSport)
                .GET("/api/v1/sport", sportHandler::searchSports)
                .build();
    }
}
