package com.sms.controller;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sms.vo.Fee;

//@FeignClient(name="smsfeeservice", url="localhost:8000")
//@FeignClient(name="smsfeeservice")
@FeignClient(name="sms-api-gateway")
@RibbonClient(name="smsfeeservice")
public interface ServiceProxy {

	//@GetMapping("/studentFee/studentId/{studentId}")
	@GetMapping("/smsfeeservice/studentFee/studentId/{studentId}")
	public Fee getStudentFeeFeign(@PathVariable("studentId") Integer studentId);
}
