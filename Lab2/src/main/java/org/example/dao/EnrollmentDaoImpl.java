package org.example.dao;

import org.example.config.DatabaseConnection;
import org.example.model.Course;
import org.example.model.Enrollment;
import org.example.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDaoImpl implements EnrollmentDao {
    @Override
    public void enrollStudent(int studentId, int courseId) throws SQLException {
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
        }

    }

    @Override
    public void setGrade(int studentId, int courseId, double grade) throws SQLException {
        String sql = "UPDATE enrollments SET grade = ? WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, grade);
            stmt.setInt(2, studentId);
            stmt.setInt(3, courseId);
            stmt.executeUpdate();
        }

    }

    @Override
    public List<Course> getCoursesForStudent(int studentId) throws SQLException {
        String sql = "SELECT c.id, c.code, c.name, c.credit_hours " +
                "FROM courses c JOIN enrollments e ON c.id = e.course_id " +
                "WHERE e.student_id = ? ORDER BY c.id";
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(new Course(
                            rs.getInt("id"),
                            rs.getString("code"),
                            rs.getString("name"),
                            rs.getInt("credit_hours")));
                }
            }
        }
        return courses;

    }

    @Override
    public List<Student> getStudentsForCourse(int courseId) throws SQLException {
        String sql = "SELECT s.id, s.name, s.major " +
                "FROM students s JOIN enrollments e ON s.id = e.student_id " +
                "WHERE e.course_id = ? ORDER BY s.id";
        List<Student> students = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("major")));
                }
            }
        }
        return students;
    }

    @Override
    public void deleteEnrollment(int studentId, int courseId) throws SQLException {
        String sql = "DELETE FROM enrollments WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
        }

    }

    @Override
    public List<Enrollment> getAllEnrollments() throws SQLException {
        String sql = "SELECT " +
                "s.id AS student_id, s.name AS student_name, s.major, " +
                "c.id AS course_id, c.code, c.name AS course_name, c.credit_hours, " +
                "e.grade, e.enrollment_date " +
                "FROM enrollments e " +
                "JOIN students s ON e.student_id = s.id " +
                "JOIN courses c ON e.course_id = c.id " +
                "ORDER BY s.id, c.id";
        List<Enrollment> enrollments = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("major")
                );
                Course course = new Course(
                        rs.getInt("course_id"),
                        rs.getString("code"),
                        rs.getString("course_name"),
                        rs.getInt("credit_hours")
                );
                Double grade = rs.getObject("grade") != null ? rs.getDouble("grade") : null;
                java.sql.Timestamp ts = rs.getTimestamp("enrollment_date");
                java.time.LocalDateTime date = ts != null ? ts.toLocalDateTime() : null;
                enrollments.add(new Enrollment(student, course, grade, date));
            }
        }
        return enrollments;
    }

    @Override
    public Enrollment getEnrollment(int studentId, int courseId) throws SQLException {
        String sql = "SELECT " +
                "s.id AS student_id, s.name AS student_name, s.major, " +
                "c.id AS course_id, c.code, c.name AS course_name, c.credit_hours, " +
                "e.grade, e.enrollment_date " +
                "FROM enrollments e " +
                "JOIN students s ON e.student_id = s.id " +
                "JOIN courses c ON e.course_id = c.id " +
                "WHERE e.student_id = ? AND e.course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student(
                            rs.getInt("student_id"),
                            rs.getString("student_name"),
                            rs.getString("major")
                    );
                    Course course = new Course(
                            rs.getInt("course_id"),
                            rs.getString("code"),
                            rs.getString("course_name"),
                            rs.getInt("credit_hours")
                    );
                    Double grade = rs.getObject("grade") != null ? rs.getDouble("grade") : null;
                    java.sql.Timestamp ts = rs.getTimestamp("enrollment_date");
                    java.time.LocalDateTime date = ts != null ? ts.toLocalDateTime() : null;
                    return new Enrollment(student, course, grade, date);
                }
            }
        }
        return null;
    }
}
