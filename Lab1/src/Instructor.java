import java.util.Set;

public class Instructor extends Person{
    private String department;
    private final Set<Course> courses;


    public Instructor(int id, int name, Set<Course> courses, String department) {
        super(id, name);
        this.department = department;
        this.courses = courses;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        if (course != null) {
            courses.add(course);
            if (course.getInstructor() != this) {
                course.setInstructor(this);
            }
        }
    }
}
