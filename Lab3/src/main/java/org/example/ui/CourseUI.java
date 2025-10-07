package org.example.ui;

import org.example.dao.CourseDao;
import org.example.dao.CourseDaoImpl;
import org.example.model.Course;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CourseUI {
    Scanner scanner = new Scanner(System.in);
    CourseDao courseDAO = new CourseDaoImpl();
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
    public void addCourse() {
        System.out.print("Enter course code: ");
        String code = scanner.nextLine().trim();
        if (code.isEmpty()) {
            System.out.println("Course code cannot be empty.");
            return;
        }
        System.out.print("Enter course name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Course name cannot be empty.");
            return;
        }
        int credits = readInt("Enter credit hours: ");
        if (credits <= 0) {
            System.out.println("Credit hours must be positive.");
            return;
        }
        Course course = new Course(code, name, credits);
        try {
            courseDAO.addCourse(course);
            System.out.println("Added course with ID " + course.getCourseId());
        } catch (SQLException ex) {
            System.err.println("Error adding course: " + ex.getMessage());
        }
    }
    public void listCourses() {
        try {
            List<Course> courses = courseDAO.getAllCourses();
            if (courses.isEmpty()) {
                System.out.println("No courses found.");
            } else {
                System.out.println("Courses:");
                for (Course c : courses) {
                    System.out.println(" - " + c);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error listing courses: " + ex.getMessage());
        }
    }
    public void updateCourse() {
        int id = readInt("Enter course ID to update: ");
        try {
            Course course = courseDAO.getCourse(id);
            if (course == null) {
                System.out.println("Course not found.");
                return;
            }
            System.out.println("Current details: " + course);
            System.out.print("Enter new code (leave blank to keep current): ");
            String code = scanner.nextLine().trim();
            System.out.print("Enter new name (leave blank to keep current): ");
            String name = scanner.nextLine().trim();
            String creditStr;
            int credits;
            System.out.print("Enter new credit hours (leave blank to keep current): ");
            creditStr = scanner.nextLine().trim();
            if (!code.isEmpty()) course.setCourseCode(code);
            if (!name.isEmpty()) course.setCourseName(name);
            if (!creditStr.isEmpty()) {
                try {
                    credits = Integer.parseInt(creditStr);
                    course.setCreditHours(credits);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid credit hours. Keeping current value.");
                }
            }
            courseDAO.updateCourse(course);
            System.out.println("Course updated.");
        } catch (SQLException ex) {
            System.err.println("Error updating course: " + ex.getMessage());
        }
    }
    public void deleteCourse() {
        int id = readInt("Enter course ID to delete: ");
        try {
            Course course = courseDAO.getCourse(id);
            if (course == null) {
                System.out.println("Course not found.");
                return;
            }
            courseDAO.deleteCourse(id);
            System.out.println("Course deleted.");
        } catch (SQLException ex) {
            System.err.println("Error deleting course: " + ex.getMessage());
        }
    }

}
