package org.example.ui;

import org.example.dao.*;
import org.example.model.Course;
import org.example.model.Enrollment;
import org.example.model.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EnrollmentUI {
    Scanner scanner = new Scanner(System.in);
    CourseDao courseDao = new CourseDaoImpl();
    StudentDao studentDao = new StudentDaoImpl();
    EnrollmentDao enrollmentDao = new EnrollmentDaoImpl();
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    public void enrollStudent() {
        int studentId = readInt("Enter student ID: ");
        int courseId = readInt("Enter course ID: ");
        try {
            // Validate that the student and course exist
            Student student = studentDao.getStudent(studentId);
            Course course = courseDao.getCourse(courseId);
            if (student == null) {
                System.out.println("Student with ID " + studentId + " does not exist.");
                return;
            }
            if (course == null) {
                System.out.println("Course with ID " + courseId + " does not exist.");
                return;
            }
            // Check if enrolment already exists
            if (enrollmentDao.getEnrollment(studentId, courseId) != null) {
                System.out.println("Student " + studentId + " is already enrolled in course " + courseId);
                return;
            }
            enrollmentDao.enrollStudent(studentId, courseId);
            System.out.println("Student " + studentId + " enrolled in course " + courseId);
        } catch (SQLException ex) {
            System.err.println("Error enrolling student: " + ex.getMessage());
        }
    }
    public void assignGrade() {
        int studentId = readInt("Enter student ID: ");
        int courseId = readInt("Enter course ID: ");
        double grade;
        while (true) {
            grade = readDouble("Enter grade (0–100): ");
            if (grade < 0 || grade > 100) {
                System.out.println("Grade must be between 0 and 100. Please try again.");
            } else {
                break;
            }
        }
        try {
            // Check that the enrolment exists
            Enrollment enrol = enrollmentDao.getEnrollment(studentId, courseId);
            if (enrol == null) {
                System.out.println("No enrolment found for student " + studentId + " in course " + courseId);
                return;
            }
            enrollmentDao.setGrade(studentId, courseId, grade);
            System.out.println("Assigned grade for student " + studentId + " in course " + courseId);
        } catch (SQLException ex) {
            System.err.println("Error assigning grade: " + ex.getMessage());
        }
    }
    public void showCoursesForStudent() {
        int studentId = readInt("Enter student ID: ");
        try {
            List<Course> courses = enrollmentDao.getCoursesForStudent(studentId);
            if (courses.isEmpty()) {
                System.out.println("The student is not enrolled in any courses.");
            } else {
                System.out.println("Courses for student " + studentId + ":");
                for (Course c : courses) {
                    System.out.println(" - " + c);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving courses: " + ex.getMessage());
        }
    }
    public void showStudentsForCourse() {
        int courseId = readInt("Enter course ID: ");
        try {
            List<Student> students = enrollmentDao.getStudentsForCourse(courseId);
            if (students.isEmpty()) {
                System.out.println("No students are enrolled in this course.");
            } else {
                System.out.println("Students in course " + courseId + ":");
                for (Student s : students) {
                    System.out.println(" - " + s);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving students: " + ex.getMessage());
        }
    }
    public void listEnrollments() {
        try {
            List<Enrollment> enrollments = enrollmentDao.getAllEnrollments();
            if (enrollments.isEmpty()) {
                System.out.println("No enrolments found.");
            } else {
                System.out.println("Enrolments:");
                for (Enrollment e : enrollments) {
                    Student s = e.getStudent();
                    Course c = e.getCourse();
                    String gradeStr = e.getGrade() != null ? String.format("%.2f", e.getGrade()) : "(no grade)";
                    System.out.printf(" - Student %s (ID %d) enrolled in %s (ID %d), grade: %s%n",
                            s.getStudentName(), s.getStudentId(), c.getCourseName(), c.getCourseId(), gradeStr);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error listing enrolments: " + ex.getMessage());
        }
    }




}
