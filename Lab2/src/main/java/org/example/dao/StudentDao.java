package org.example.dao;

import org.example.model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDao {
    void addStudent(Student student) throws SQLException;
    Student getStudent(int id) throws SQLException;
    List<Student> getAllStudents() throws SQLException;
    void updateStudent(Student student) throws SQLException;
    void deleteStudent(int id) throws SQLException;
}
