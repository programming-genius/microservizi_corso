package it.app.aggregator.start;

import it.app.aggregator.security.ProjectConfigSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan({"it.app.aggregator.component","it.app.aggregator.web","it.app.aggregator.service"})
@Import({ProjectConfigSecurity.class})
@EnableHystrix
public class AggegatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AggegatorApplication.class, args);
	}

}
