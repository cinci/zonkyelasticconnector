package com.jctechcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application class
 * <p>
 * Created by jcincera on 15/06/16.
 */
@EnableScheduling
@SpringBootApplication
public class ZonkyElasticConnectorApplication {

    /**
     * Main
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ZonkyElasticConnectorApplication.class, args);
    }
}
