package org.example.model;

import java.time.LocalDateTime;

public class Enrollment {
    private Student student;
    private Course course;
    private Double grade;
    private java.time.LocalDateTime enrollmentDate;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public Enrollment(Student student, Course course, Double grade) {
        this.student = student;
        this.course = course;
        this.grade = grade;
    }

    public Enrollment(Student student, Course course, Double grade, LocalDateTime enrollmentDate) {
        this.student = student;
        this.course = course;
        this.grade = grade;
        this.enrollmentDate = enrollmentDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "student=" + student +
                ", course=" + course +
                ", grade=" + grade +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }
}
