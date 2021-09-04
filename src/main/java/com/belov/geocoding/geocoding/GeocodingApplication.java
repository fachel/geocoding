package com.belov.geocoding.geocoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class GeocodingApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeocodingApplication.class, args);
    }

}
