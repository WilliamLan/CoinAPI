package org.coin.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = { "org.coin.config", "org.coin.controller", "org.coin.service", "org.coin.service.impl",
		"org.coin.component", "org.coin.repository" })

public class CoinDeskApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CoinDeskApplication.class);
		application.run(args);
	}

}
