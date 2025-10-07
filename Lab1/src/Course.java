import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Course {
    private String code;
    private String name;
    private int creditHours;
    private Instructor instructor;
    private final Set<Student> students;

    public Course(String code, String name, int creditHours) {
        this.code = code;
        this.name = name;
        this.creditHours = creditHours;
        this.students = new HashSet<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {

        this.creditHours = creditHours;
    }

    public Instructor getInstructor() {

        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
        if (instructor != null) {
            instructor.addCourse(this);
        }
    }

    public void addStudent(Student student) {
        if (student != null) {
            students.add(student);
        }
    }

    public void removeStudent(Student student) {

        students.remove(student);
    }

    public Set<Student> getStudents() {

        return Collections.unmodifiableSet(students);
    }

    @Override
    public String toString() {
        return String.format("Course{code='%s', name='%s', creditHours=%d, enrolled=%d}",
                code, name, creditHours, students.size());
    }
}
