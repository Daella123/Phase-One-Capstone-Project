import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends Person{
    private String major;
    private Map<Course, Double> courseGrades;

    public Student() {
        this(0, "Anonymous", "Undeclared");
    }

    public Student(int id, String name, String major) {
        super(id, name);
        this.major = major;
        this.courseGrades = new HashMap<>();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void enrolInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("course cannot be null");
        }
        courseGrades.putIfAbsent(course, null);
        course.addStudent(this);
    }

    public void setGrade(Course course, double grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("grade must be between 0 and 100");
        }
        courseGrades.put(course, grade);
        if (!course.getStudents().contains(this)) {
            course.addStudent(this);
        }
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courseGrades.keySet());
    }

    public double calculateGPA() {
        double total = 0;
        int count = 0;
        for (Double grade : courseGrades.values()) {
            if (grade != null) {
                total += convertGradeToPoints(grade);
                count++;
            }
        }
        return count == 0 ? 0 : total / count;
    }

    protected double convertGradeToPoints(double grade) {
        if (grade >= 90) return 4.0;
        if (grade >= 80) return 3.0;
        if (grade >= 70) return 2.0;
        if (grade >= 60) return 1.0;
        return 0.0;
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', major='%s', GPA=%.2f}",
                getId(), getName(), major, calculateGPA());
    }
}
