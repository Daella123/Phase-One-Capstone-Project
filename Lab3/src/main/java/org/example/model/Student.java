package org.example.model;

public class Student {
    private int studentId;
    private String studentName;
    private String major;

    public Student() {
    }

    public Student(String studentName, String major) {
        this.studentName = studentName;
        this.major = major;
    }

    public Student(int studentId, String studentName, String major) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.major = major;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", major='" + major + '\'' +
                '}';
    }
}
