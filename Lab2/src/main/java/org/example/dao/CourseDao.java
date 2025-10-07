package org.example.dao;

import org.example.model.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseDao {
    void addCourse(Course course) throws SQLException;
    Course getCourse(int id) throws SQLException;
    List<Course> getAllCourses() throws SQLException;
    void updateCourse(Course course) throws SQLException;
    void deleteCourse(int id) throws SQLException;
}
