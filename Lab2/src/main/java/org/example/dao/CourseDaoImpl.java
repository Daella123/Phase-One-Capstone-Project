package org.example.dao;

import org.example.config.DatabaseConnection;
import org.example.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao{
    @Override
    public void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO courses (code, name, credit_hours) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getCreditHours());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    course.setCourseId(keys.getInt(1));
                }
                System.out.println("Student inserted successfully: " + course.getCourseName()+" with id:"+course.getCourseId());
            }
        }
    }

    @Override
    public Course getCourse(int id) throws SQLException {
        String sql = "SELECT id, code, name, credit_hours FROM courses WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(rs.getInt("id"), rs.getString("code"), rs.getString("name"), rs.getInt("credit_hours"));
                }
                return null;
            }
        }
    }

    @Override
    public List<Course> getAllCourses() throws SQLException {
        String sql = "SELECT id, code, name, credit_hours FROM courses ORDER BY id";
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                courses.add(new Course(rs.getInt("id"), rs.getString("code"), rs.getString("name"), rs.getInt("credit_hours")));
            }
        }
        return courses;
    }

    @Override
    public void updateCourse(Course course) throws SQLException {
        String sql = "UPDATE courses SET code = ?, name = ?, credit_hours = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getCreditHours());
            stmt.setInt(4, course.getCourseId());
            int rowsUpdated=stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Updated student with id " + course.getCourseId());
            }
        }

    }

    @Override
    public void deleteCourse(int id) throws SQLException {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Deleted student with id " + id);
            }
        }

        }
    }
