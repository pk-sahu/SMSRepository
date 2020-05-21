package com.sms.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.model.Student;

public interface StudentDAO extends CrudRepository<Student, Integer>{

	List<Student> findByCreatedBy(String studentName);
}
