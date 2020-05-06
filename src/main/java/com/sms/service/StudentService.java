package com.sms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.sms.dao.StudentDAO;
import com.sms.model.Student;

@Service
public class StudentService {
	
	@Autowired
	private StudentDAO studentDAO;
	
	public Student getStudentById(Integer id){
		Optional<Student> student = studentDAO.findById(id);
		return student.get();
	}
	
	public List<Student> getAllStudents() {
		Iterable<Student> studentList = studentDAO.findAll();
		List<Student> studentArrayList = new ArrayList<Student>();
		for(Student student : studentList) 
			studentArrayList.add(student);
		return studentArrayList;	
	}
	
	public void addStudent(Student student){
		studentDAO.save(student);
	}
	
	public Student updateStudent(Student student){
		Optional<Student> fetchedStudent = studentDAO.findById(student.getId());
		fetchedStudent.get().setPhone(student.getPhone());
		Student upadedStudent = studentDAO.save(fetchedStudent.get());
		return upadedStudent;
	}
	
	public void deleteStudent(Integer id){
		studentDAO.deleteById(id);
	}
}