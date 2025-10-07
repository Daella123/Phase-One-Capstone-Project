public class GraduateStudent extends Student{
    public GraduateStudent(int id, String name, String major) {
        super(id, name, major);
    }

    public double calculateGPA() {

        double baseGpa = super.calculateGPA();
        double curved = baseGpa + 0.2;
        return Math.min(curved, 4.0);
    }
}
