package com.parking.administration;

import com.parking.administration.demo.dto.request.UserRegistrationRequest;
import com.parking.administration.demo.service.RegistrationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

import static com.parking.administration.demo.domain.enums.UserRole.ADMIN;
import static com.parking.administration.demo.utils.Utility.LOGGER;

@EnableAutoConfiguration
@EnableJpaRepositories("com.parking.administration.demo.repository")
@EnableTransactionManagement
@EntityScan("com.parking.administration.demo")
@ComponentScan(basePackages = {"com.parking.administration.demo"})
@RestController
@SpringBootApplication
public class ParkingAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingAdminApplication.class, args);
	}

}
