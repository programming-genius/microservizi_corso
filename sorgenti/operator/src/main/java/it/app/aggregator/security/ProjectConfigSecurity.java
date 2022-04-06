package it.app.aggregator.security;

import it.app.aggregator.component.JWTAuthenticationProvider;
import it.app.aggregator.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class ProjectConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
    private JWTAuthenticationProvider jwtAuthentication;

	private JwtAuthenticationFilter authenticationFilter() throws Exception {
		return new JwtAuthenticationFilter(getAuthenticationManager());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(jwtAuthentication);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(authenticationFilter(), BasicAuthenticationFilter.class).authorizeRequests().
				antMatchers("/aggregate").hasAnyAuthority("Client","Admin").
				and().csrf().disable().httpBasic().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/actuator/**");
	}

    @Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

    @Bean
    public HttpTraceRepository httpTraceRepository() {
	    return new InMemoryHttpTraceRepository();
	}

}