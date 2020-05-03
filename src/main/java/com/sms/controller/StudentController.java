package com.sms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/sms")
public class StudentController {

	@Autowired
	private StudentDAO studentDAO;
	
	@GetMapping("/student/{id}")
	//@RequestMapping(value="/student/{id}", method=RequestMethod.GET)
	public ResponseEntity<Student> getStudentById(@PathVariable("id") Integer id) {
		Optional<Student> student = studentDAO.findById(id);		
		return new ResponseEntity<Student>(student.get(), HttpStatus.OK);
	}
	
	@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStudents() {
		Iterable<Student> studentList = studentDAO.findAll();
		List<Student> studentArrayList = new ArrayList<Student>();
		for(Student student : studentList) 
			studentArrayList.add(student);
		
		return new ResponseEntity<List<Student>>(studentArrayList, HttpStatus.OK);
	}
	
	@PostMapping("/student")
	//@RequestMapping(value="/student/{id}", method=RequestMethod.POST)
	public ResponseEntity<String> addStudent(@RequestBody Student student, UriComponentsBuilder builder) {
		studentDAO.save(student);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/student/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/student")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
		Optional<Student> fetchedStudent = studentDAO.findById(student.getId());
		fetchedStudent.get().setPhone(student.getPhone());
		Student upadedStudent = studentDAO.save(fetchedStudent.get());
		return new ResponseEntity<Student>(upadedStudent, HttpStatus.OK);
	}
	
	@DeleteMapping("/student/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable("id") Integer id) {
		studentDAO.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value="/basicStd/123")
	public String getStudent(){
		return "Student 123";
	}
	@GetMapping(value="/admin/456")
	public String getStudentForAdmin(){
		return "Student 456";
	}
}
