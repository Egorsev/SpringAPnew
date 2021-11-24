package ru.latyshev.app.SpringAPI.configurations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SpringApIv5Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringApIv5Application.class, args);
	}

}
