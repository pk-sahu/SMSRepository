package com.sms.dao;

import org.springframework.data.repository.CrudRepository;

import com.sms.model.Student;

public interface StudentDAO extends CrudRepository<Student, Integer>{

}
