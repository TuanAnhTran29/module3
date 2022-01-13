package com.service.studentservice;

import com.model.Student;
import com.service.IGeneralService;

import java.util.List;

public interface IStudentService extends IGeneralService<Student> {
    public void add(Student student);

    public List<Student> findAll();

    public Student findById(int id);

    public void update(int id,Student student);

    public void delete(int id);
}
