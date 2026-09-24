import java.util.HashSet;

public class Company {

    private int companyId;
    private String companyName;
    private double cgpaCutoff;
    private HashSet<String> requiredSkills;

    public Company(int companyId, String companyName,
                   double cgpaCutoff, HashSet<String> requiredSkills) {

        this.companyId = companyId;
        this.companyName = companyName;
        this.cgpaCutoff = cgpaCutoff;
        this.requiredSkills = requiredSkills;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getCgpaCutoff() {
        return cgpaCutoff;
    }

    public HashSet<String> getRequiredSkills() {
        return requiredSkills;
    }

    @Override
    public String toString() {
        return "Company{" +
                "ID=" + companyId +
                ", Name='" + companyName + '\'' +
                ", CGPA Cutoff=" + cgpaCutoff +
                ", Required Skills=" + requiredSkills +
                '}';
    }
}