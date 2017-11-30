package com.epam.mck.configServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


/** 
 * Central Cloud Config Server for rest of the services.  
 * 
 * Configuration Repository URL : https://github.com/PhaniRaag/CloudConfig
 * 
 * @author Raghavendra_Phani
 * 
 * */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
