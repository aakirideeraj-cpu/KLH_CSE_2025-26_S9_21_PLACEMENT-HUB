package com.placementhub.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Model representing a Company Recruitment Drive in Placement Hub.
 * Contains eligibility criteria (Min CGPA, Required Skills), intake quota,
 * and drive metadata.
 */
public class CompanyDrive {
    private int id;
    private String driveCode;
    private String companyName;
    private String roleTitle;
    private double minCgpa;
    private Set<String> requiredSkills;
    private int maxIntake; // Capacity limit for network flow modeling (CO4)
    private double ctcLpa;
    private String description;
    private String interviewDate;
    private String status; // UPCOMING, ACTIVE, COMPLETED

    public CompanyDrive() {
        this.requiredSkills = new HashSet<>();
        this.status = "ACTIVE";
    }

    public CompanyDrive(int id, String driveCode, String companyName, String roleTitle,
                        double minCgpa, Set<String> requiredSkills, int maxIntake,
                        double ctcLpa, String description, String interviewDate) {
        this.id = id;
        this.driveCode = driveCode;
        this.companyName = companyName;
        this.roleTitle = roleTitle;
        this.minCgpa = minCgpa;
        this.requiredSkills = requiredSkills != null ? new HashSet<>(requiredSkills) : new HashSet<>();
        this.maxIntake = maxIntake;
        this.ctcLpa = ctcLpa;
        this.description = description != null ? description : "";
        this.interviewDate = interviewDate != null ? interviewDate : "2026-10-15";
        this.status = "ACTIVE";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDriveCode() { return driveCode; }
    public void setDriveCode(String driveCode) { this.driveCode = driveCode; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getRoleTitle() { return roleTitle; }
    public void setRoleTitle(String roleTitle) { this.roleTitle = roleTitle; }

    public double getMinCgpa() { return minCgpa; }
    public void setMinCgpa(double minCgpa) { this.minCgpa = minCgpa; }

    public Set<String> getRequiredSkills() { return Collections.unmodifiableSet(requiredSkills); }
    public void setRequiredSkills(Set<String> requiredSkills) {
        this.requiredSkills = requiredSkills != null ? new HashSet<>(requiredSkills) : new HashSet<>();
    }
    public void addRequiredSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty()) {
            this.requiredSkills.add(skill.trim());
        }
    }

    public int getMaxIntake() { return maxIntake; }
    public void setMaxIntake(int maxIntake) { this.maxIntake = maxIntake; }

    public double getCtcLpa() { return ctcLpa; }
    public void setCtcLpa(double ctcLpa) { this.ctcLpa = ctcLpa; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInterviewDate() { return interviewDate; }
    public void setInterviewDate(String interviewDate) { this.interviewDate = interviewDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRequiredSkillsCsv() {
        return String.join(", ", requiredSkills);
    }

    /**
     * Checks if a candidate satisfies basic eligibility criteria:
     * CGPA >= minCgpa AND Candidate possesses all mandatory skills.
     */
    public boolean isStudentEligible(Student s) {
        if (s == null) return false;
        if (s.getCgpa() < this.minCgpa) return false;
        return s.hasAllSkills(this.requiredSkills);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s | Min CGPA: %.2f | Intake: %d | CTC: %.1f LPA | Req: %s",
                driveCode, companyName, roleTitle, minCgpa, maxIntake, ctcLpa, getRequiredSkillsCsv());
    }
}
