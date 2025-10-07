import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Instructor extends Person {
    private String department;
    private final Set<Course> courses;

    public Instructor(int id, String name, String department) {
        super(id, name);
        this.department = department;
        this.courses = new HashSet<>();
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void addCourse(Course course) {
        if (course != null) {
            courses.add(course);
            if (course.getInstructor() != this) {
                course.setInstructor(this);
            }
        }
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public Set<Course> getCourses() {
        return Collections.unmodifiableSet(courses);
    }

    @Override
    public String toString() {
        return String.format("Instructor{id=%d, name='%s', dept='%s', courses=%d}",
                getId(), getName(), department, courses.size());
    }
}
