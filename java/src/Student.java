import java.util.HashSet;

public class Student {

    private int studentId;
    private String name;
    private double cgpa;
    private HashSet<String> skills;

    public Student(int studentId, String name, double cgpa, HashSet<String> skills) {
        this.studentId = studentId;
        this.name = name;
        this.cgpa = cgpa;
        this.skills = skills;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public double getCgpa() {
        return cgpa;
    }

    public HashSet<String> getSkills() {
        return skills;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + studentId +
                ", Name='" + name + '\'' +
                ", CGPA=" + cgpa +
                ", Skills=" + skills +
                '}';
    }
}