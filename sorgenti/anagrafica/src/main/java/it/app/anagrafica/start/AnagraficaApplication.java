package it.app.anagrafica.start;

import it.app.anagrafica.security.ProjectConfigSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"it.app.anagrafica.service","it.app.anagrafica.component","it.app.anagrafica.web"})
@EnableJpaRepositories({"it.app.anagrafica.repository"})
@EntityScan({"it.app.anagrafica.entity"})
@Import({ProjectConfigSecurity.class})
public class AnagraficaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnagraficaApplication.class, args);
	}

}
