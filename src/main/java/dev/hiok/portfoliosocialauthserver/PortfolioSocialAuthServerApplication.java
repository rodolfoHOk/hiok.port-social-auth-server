package dev.hiok.portfoliosocialauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import dev.hiok.portfoliosocialauthserver.core.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class PortfolioSocialAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioSocialAuthServerApplication.class, args);
	}

}
