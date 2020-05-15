package com.sms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sms.filter.JwtRequestFilter;
import com.sms.filter.TokenBasedAuthenticationFilter;
import com.sms.service.UserDetailsServiceImpl;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;	
	
	@Autowired
	private AppAuthenticationEntryPoint appAuthenticationEntryPoint;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.exceptionHandling().authenticationEntryPoint(appAuthenticationEntryPoint);
		
		JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(authenticationManager());
		jwtRequestFilter.setJwtUserDetailsService(userDetailsServiceImpl);
		http.httpBasic().disable()
			.formLogin().disable()
			.logout().disable()
			.addFilter(jwtRequestFilter)
			.addFilter(new TokenBasedAuthenticationFilter(authenticationManager()))
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
		
		http.authorizeRequests().antMatchers("/sms/authenticate").permitAll()
								.antMatchers("/sms/internationalized").permitAll()
								.antMatchers("/sms/filtering/**").permitAll()
								.antMatchers("/versioning/**").permitAll()
								.antMatchers("/sms/studentFee/**").permitAll()
								//.antMatchers("/sms/admin/**").hasAnyRole("ADMIN")
								//.antMatchers("/sms/user/**").hasAnyRole("USER")
								.antMatchers("/sms/**").hasAnyRole("ADMIN","USER")
								.antMatchers("/sms/**").authenticated();
		http.headers().cacheControl();
		
		/*http.csrf().disable().authorizeRequests().antMatchers("/sms/basicStd/**")
			.hasAnyRole("admin","user").and().formLogin();
		http.csrf().disable().authorizeRequests().antMatchers("/sms/admin/**")
			.hasAnyRole("admin").and().formLogin();*/
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials use BCryptPasswordEncoder
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
			    
		/*auth.inMemoryAuthentication().withUser("akshay").password("{noop}akshay@1").roles("user");
		auth.inMemoryAuthentication().withUser("kedar").password("{noop}kedar@1").roles("user","admin");*/
	}
}
