package it.app.mobile.start;

import it.app.mobile.security.ProjectConfigSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"it.app.mobile.component","it.app.mobile.service","it.app.mobile.web"})
@EnableJpaRepositories({"it.app.mobile.repository"})
@EntityScan({"it.app.mobile.entity"})
@Import({ProjectConfigSecurity.class})
public class MobileApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileApplication.class, args);
	}

}
