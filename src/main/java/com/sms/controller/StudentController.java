package com.sms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sms.dao.StudentDAO;
import com.sms.model.Student;
import com.sms.service.StudentService;

@RestController
@RequestMapping("/sms")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@GetMapping("/student/{id}")
	//@RequestMapping(value="/student/{id}", method=RequestMethod.GET)
	public ResponseEntity<Student> getStudentById(@PathVariable("id") Integer id) {
		Student student = studentService.getStudentById(id);		
		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}
	
	@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStudents() {
		List<Student> studentArrayList = studentService.getAllStudents();
		return new ResponseEntity<List<Student>>(studentArrayList, HttpStatus.OK);
	}
	
	@PostMapping("/student")
	//@RequestMapping(value="/student/{id}", method=RequestMethod.POST)
	public ResponseEntity<String> addStudent(@RequestBody Student student, UriComponentsBuilder builder) {
		studentService.addStudent(student);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/student/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
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
	
}
