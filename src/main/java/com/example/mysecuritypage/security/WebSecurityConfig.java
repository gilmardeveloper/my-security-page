package com.example.mysecuritypage.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * Classe de configurações do spring security
 * 
 * @author Gilmar Carlos
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
		
	@Autowired
	private DetailsService detailsService;
	
	@Autowired
	private PasswordCrypt passwordCrypt;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/css","/css/**").permitAll()
		.antMatchers("/fonts","/fonts/**").permitAll()
		.antMatchers("/js","/js/**").permitAll()
		.antMatchers("/register","/register/**").permitAll()
		.antMatchers("/dashboard/admin","/dashboard/admin/**").hasAnyRole("Super")
		.antMatchers("/dashboard","/dashboard/**").hasAnyRole("Super","Admin","User")
		.antMatchers("/","/**").hasAnyRole("User")
		.anyRequest().authenticated()
		.and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .defaultSuccessUrl("/dashboard/", true)
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .and()
        .headers().defaultsDisabled().cacheControl();
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(detailsService);
	    authProvider.setPasswordEncoder(passwordCrypt);
	    return authProvider;
	}
	
	
}
