package com.tenpo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.tenpo.rest", "com.tenpo.service", "com.tenpo.external", "com.tenpo.config"})
@EntityScan(basePackages = {"com.tenpo.model", "com.tenpo.external", "com.tenpo.config"})
@EnableJpaRepositories("com.tenpo.repository")
public class TenpoChallengeApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(TenpoChallengeApplication.class, args);
    }
}
