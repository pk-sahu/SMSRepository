package com.sms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sms.vo.Fee;
import com.sms.dao.StudentDAO;
import com.sms.exception.UserNotFoundException;
import com.sms.model.Student;
import com.sms.service.StudentService;

@RestController
@RequestMapping("/sms")
public class StudentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	private MessageSource messageSource; 
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private ServiceProxy serviceProxy;
	
	@GetMapping("/student/{id}")
	//@RequestMapping(value="/student/{id}", method=RequestMethod.GET)
	public Resource<Student> getStudentById(@PathVariable("id") Integer id) {
		Student student = studentService.getStudentById(id);	
		
		// Hateoas
		Resource<Student> resource = new Resource<Student>(student);
		
		ControllerLinkBuilder linkTo = 
				ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getAllStudents());
		
		resource.add(linkTo.withRel("all-students"));
		
		return resource;
		//return new ResponseEntity<Student>(student, HttpStatus.OK);
	}
	
	@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStudents() {
		List<Student> studentArrayList = studentService.getAllStudents();
		return new ResponseEntity<List<Student>>(studentArrayList, HttpStatus.OK);
	}
	
	@PostMapping("/student")
	//@RequestMapping(value="/student/{id}", method=RequestMethod.POST)
	public ResponseEntity<String> addStudent(@Valid @RequestBody Student student, UriComponentsBuilder builder) {
		studentService.addStudent(student);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/student/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        
        /* Alternate approach
         * URI location = ServletUriComponentsBuilder
    			.fromCurrentRequest()
    			.path("/{id}")
    			.buildAndExpand(student.getId()).toUri();
    	   return ResponseEntity.created(location).build();	*/
	}
	
	@PutMapping("/student")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
		Student upadedStudent = studentService.updateStudent(student);
		return new ResponseEntity<Student>(upadedStudent, HttpStatus.OK);
	}
	
	@DeleteMapping("/student/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable("id") Integer id) {
		studentService.deleteStudent(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@Secured ({"ROLE_USER"})
	@GetMapping(value="/user/123")
	public String getStudent(){
		return "I am user with id # 123";
	}
	
	@Secured ({"ROLE_ADMIN"})
	@GetMapping(value="/admin/456")
	public String getStudentForAdmin(){
		return "I am Admin with id # 456";
	}
	
	@GetMapping(path = "/internationalized")
	public String internationalized() {
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
	/*@GetMapping(path = "/internationalized")
	public String internationalized(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
		return messageSource.getMessage("good.morning.message", null, locale);
	}*/
	
	@GetMapping("/filtering/{id}")
	public MappingJacksonValue retrieveSomeBean(@PathVariable("id") Integer id) {
		Student student = studentService.getStudentById(id);	
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("caste", "phone");
		FilterProvider filters = new SimpleFilterProvider().addFilter("studentFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(student);
		mapping.setFilters(filters);
		return mapping;
	}
	
	@GetMapping("/filtering/list")
	public MappingJacksonValue retrieveListOfSomeBeans() {
		List<Student> studentArrayList = studentService.getAllStudents();
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("caste", "phone");
		FilterProvider filters = new SimpleFilterProvider().addFilter("studentFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(studentArrayList);
		mapping.setFilters(filters);
		return mapping;
	}
	
	@GetMapping("/studentFee/studentId/{studentId}")
	public ResponseEntity<Fee> getStudentFee(@PathVariable Integer studentId){
		
		Map<String, Integer> uriVariables = new HashMap<>();
		uriVariables.put("studentId", studentId);
		
		ResponseEntity<Fee> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/studentFee/studentId/{studentId}", Fee.class,
				uriVariables);
		
		Fee feeResponse = responseEntity.getBody();
		LOGGER.info("{}", feeResponse.getAmount());
		return new ResponseEntity<Fee>(feeResponse, HttpStatus.OK);
	}
	
	@GetMapping("/studentFee/Feign/studentId/{studentId}")
	public ResponseEntity<Fee> getStudentFeeFeign(@PathVariable Integer studentId){
		Fee feeResponse = serviceProxy.getStudentFeeFeign(studentId);		
		return new ResponseEntity<Fee>(feeResponse, HttpStatus.OK);
	}
}
