package org.example.dao;

import org.example.model.Course;
import org.example.model.Enrollment;
import org.example.model.Student;

import java.sql.SQLException;
import java.util.List;

public interface EnrollmentDao {
    void enrollStudent(int studentId, int courseId) throws SQLException;
    void setGrade(int studentId, int courseId, double grade) throws SQLException;
    List<Course> getCoursesForStudent(int studentId) throws SQLException;
    List<Student> getStudentsForCourse(int courseId) throws SQLException;
    void deleteEnrollment(int studentId, int courseId) throws SQLException;
    List<Enrollment> getAllEnrollments() throws SQLException;
    Enrollment getEnrollment(int studentId, int courseId) throws SQLException;

}
