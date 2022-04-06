package it.app.mobile.security;

import it.app.mobile.component.JWTAuthenticationProvider;
import it.app.mobile.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
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
				antMatchers("/user/create").hasAuthority("Admin").
				antMatchers("/user/update").hasAuthority("Admin").
				antMatchers("/user/delete").hasAuthority("Admin").
				antMatchers("/user/findById/*").hasAnyAuthority("Client","Admin").
				antMatchers("/user/findUsersByFilter/*").hasAnyAuthority("Client","Admin")
				.and()
				.csrf().disable().httpBasic().disable().
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
    public HttpTraceRepository httpTraceRepository() {
	    return new InMemoryHttpTraceRepository();
	}

}