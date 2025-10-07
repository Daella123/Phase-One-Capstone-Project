package org.example.dao;

import org.example.config.DatabaseConnection;
import org.example.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao{
    @Override
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (name, major) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, student.getStudentName());
            stmt.setString(2, student.getStudentMajor());
            stmt.executeUpdate();
            // Retrieve generated id
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    student.setStudentId(keys.getInt(1));
                }
                System.out.println("Student inserted successfully: " + student.getStudentName()+" with id:"+student.getStudentId());
            }


        }

    }

    @Override
    public Student getStudent(int id) throws SQLException {
        String sql = "SELECT id, name, major FROM students WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(rs.getInt("id"), rs.getString("name"), rs.getString("major"));
                }
                return null;
            }
        }
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        String sql = "SELECT id, name, major FROM students ORDER BY id";
        List<Student> students = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                students.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("major")));
            }
        }
        return students;
    }

    @Override
    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET name = ?, major = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getStudentName());
            stmt.setString(2, student.getStudentMajor());
            stmt.setInt(3, student.getStudentId());
            int rowsUpdated =stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Updated student with id " + student.getStudentId());
            }
        }

    }

    @Override
    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted =stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Deleted student with id " + id);
            }
        }
    }


}
