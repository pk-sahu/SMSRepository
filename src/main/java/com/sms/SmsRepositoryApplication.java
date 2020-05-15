package com.sms;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import brave.sampler.Sampler;

import com.sms.vo.MessageReader;
import com.sms.vo.SchoolAddress;


//@ComponentScan(basePackages= {"com.sms"})
@ImportResource("classpath:Beans.xml")
@SpringBootApplication
@EnableFeignClients("com.sms")
@EnableDiscoveryClient
public class SmsRepositoryApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmsRepositoryApplication.class);
	
	public static void main(String[] args) {
		//SpringApplication.run(SmsRepositoryApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(SmsRepositoryApplication.class, args);
		
		MessageReader messageReader = context.getBean(MessageReader.class);
		LOGGER.info("School Name :" + messageReader.getSchoolName());
		
		SchoolAddress schoolAddress = context.getBean(SchoolAddress.class);
		LOGGER.info("School Address :" + schoolAddress.getAddress());
		
		/* ANOTHER WAY TO CHANGE SERVER PORT
		SpringApplication springApplication =new SpringApplication(Application.class);
		Map<String,Object> configMap = new HashMap<>();
		configMap.put("SERVER_PORT", 8585);
		springApplication.setDefaultProperties(configMap);
		springApplication.run(args);*/
	}
	
	@Bean
	public Sampler defaultSampler(){
		return Sampler.ALWAYS_SAMPLE;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		//SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

	/*@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}*/
}
