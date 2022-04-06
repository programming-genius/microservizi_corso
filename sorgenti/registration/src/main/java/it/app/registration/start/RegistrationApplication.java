package it.app.registration.start;

import it.app.registration.security.ProjectConfigSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan({"it.app.registration.component","it.app.registration.web","it.app.registration.service"})
@Import({ProjectConfigSecurity.class})
@EnableHystrix
public class RegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}

}
