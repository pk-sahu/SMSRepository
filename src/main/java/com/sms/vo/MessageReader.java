package com.sms.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageReader {

	@Value(value="${school.name}")
	private String schoolName;
	
	public String getSchoolName(){
		return schoolName;
	}
}

