package com.sms.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sms.model.Student;
import com.sms.service.StudentService;
import com.sms.vo.HelloWorldBean;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class StudentResource {

	@Autowired
	private StudentService studentService;
	
	@GetMapping("/sms/{username}/students")
	public List<Student> getAllStudents(@PathVariable String username){
		return studentService.getStudentsOfUser(username);
	}

	@GetMapping("/sms/{username}/students/{id}")
	public Student getStudent(@PathVariable String username, @PathVariable int id){
		return studentService.getStudentById(id);
	}

	@DeleteMapping("/sms/{username}/students/{id}")
	public ResponseEntity<Void> deleteStudent(
			@PathVariable String username, @PathVariable int id){		
		studentService.deleteStudent(id);		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/sms/{username}/students/{id}")
	public ResponseEntity<Student> updateStudent(
			@PathVariable String username,
			@PathVariable long id, @RequestBody Student student){
		
		studentService.addStudent(student);
		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}
	
	@PostMapping("/sms/{username}/students")
	public ResponseEntity<Void> createStudent(
			@PathVariable String username, @RequestBody Student student){
		
		student.setCreatedBy(username);
		studentService.addStudent(student);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(student.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(path = "/hello-world")
	public HelloWorldBean internationalized() {
		return new HelloWorldBean("Hello World - Changed!!");
	}
	
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		//throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
		return new HelloWorldBean("Hello World - Changed");
	}
	
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}
}
