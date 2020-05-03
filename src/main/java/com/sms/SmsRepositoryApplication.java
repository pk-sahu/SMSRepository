package com.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import com.sms.vo.MessageReader;
import com.sms.vo.SchoolAddress;

@SpringBootApplication
//@ComponentScan(basePackages= {"com.sms"})
@ImportResource("classpath:Beans.xml")
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

}
