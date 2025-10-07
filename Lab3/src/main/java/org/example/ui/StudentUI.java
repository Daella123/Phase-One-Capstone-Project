package org.example.ui;

import org.example.dao.StudentDao;
import org.example.dao.StudentDaoImpl;
import org.example.model.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentUI {
    StudentDao studentDAO = new StudentDaoImpl();
    Scanner scanner = new Scanner(System.in);
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
    public void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Student name cannot be empty.");
            return;
        }
        System.out.print("Enter major: ");
        String major = scanner.nextLine().trim();
        if (major.isEmpty()) {
            System.out.println("Major cannot be empty.");
            return;
        }
        Student student = new Student(name, major);
        try {
            studentDAO.addStudent(student);
            System.out.println("Added student with ID " + student.getStudentId());
        } catch (SQLException ex) {
            System.err.println("Error adding student: " + ex.getMessage());
        }
    }

    public void listStudents() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            if (students.isEmpty()) {
                System.out.println("No students found.");
            } else {
                System.out.println("Students:");
                for (Student s : students) {
                    System.out.println(" - " + s);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error listing students: " + ex.getMessage());
        }
    }
    public void updateStudent() {
        int id = readInt("Enter student ID to update: ");
        try {
            Student student = studentDAO.getStudent(id);
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }
            System.out.println("Current details: " + student);
            System.out.print("Enter new name (leave blank to keep current): ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter new major (leave blank to keep current): ");
            String major = scanner.nextLine().trim();
            if (!name.isEmpty()) student.setStudentName(name);
            if (!major.isEmpty()) student.setMajor(major);
            studentDAO.updateStudent(student);
            System.out.println("Student updated.");
        } catch (SQLException ex) {
            System.err.println("Error updating student: " + ex.getMessage());
        }
    }
    public void deleteStudent() {
        int id = readInt("Enter student ID to delete: ");
        try {
            Student student = studentDAO.getStudent(id);
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }
            studentDAO.deleteStudent(id);
            System.out.println("Student deleted.");
        } catch (SQLException ex) {
            System.err.println("Error deleting student: " + ex.getMessage());
        }
    }

}
