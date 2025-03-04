package org.diploma.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.diploma.userservice.fw.property.KeycloakClientProperties;

@SpringBootApplication
@EnableConfigurationProperties(KeycloakClientProperties.class)
public class UserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
