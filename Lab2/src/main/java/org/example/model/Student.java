package org.example.model;

public class Student {
    private int studentId;
    private String studentName;
    private String studentMajor;

    public Student(String studentName, String studentMajor) {
        this.studentName = studentName;
        this.studentMajor = studentMajor;
    }

    public Student(int studentId, String studentName, String studentMajor) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentMajor = studentMajor;
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

    public String getStudentMajor() {
        return studentMajor;
    }

    public void setStudentMajor(String studentMajor) {
        this.studentMajor = studentMajor;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", studentMajor='" + studentMajor + '\'' +
                '}';
    }
}
