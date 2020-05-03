package com.sms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("/sms/basicStd/**")
			.hasAnyRole("admin","user")
			.and()
			.formLogin();
		http.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("/sms/admin/**")
			.hasAnyRole("admin")
			.and()
			.formLogin();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
			.withUser("akshay")
			.password("{noop}akshay@1")
			.roles("user");
		auth.inMemoryAuthentication()
			.withUser("kedar")
			.password("{noop}kedar@1")
			.roles("user","admin");
	}
}
