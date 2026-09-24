import java.util.HashSet;

public class Main {

    public static void main(String[] args) {

        HashSet<String> skills = new HashSet<>();

        skills.add("Java");
        skills.add("SQL");
        skills.add("Python");

        Student student = new Student(
                1,
                "Deeraj",
                8.50,
                skills
        );

        System.out.println("=== Placement Hub ===");
        System.out.println(student);
    }
}