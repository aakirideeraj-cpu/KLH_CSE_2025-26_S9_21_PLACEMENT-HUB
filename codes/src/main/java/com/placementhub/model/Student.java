package com.placementhub.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class representing a Student applicant in Placement Hub.
 * Contains academic records, skill tags, and resume text for pattern matching.
 */
public class Student implements Comparable<Student> {
    private int id;
    private String rollNo;
    private String name;
    private String email;
    private double cgpa;
    private Set<String> skills;
    private String resumeText;
    private int aptitudeScore; // 0 - 100
    private boolean placed;

    public Student() {
        this.skills = new HashSet<>();
    }

    public Student(int id, String rollNo, String name, String email, double cgpa,
                   Set<String> skills, String resumeText, int aptitudeScore) {
        this.id = id;
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.cgpa = cgpa;
        this.skills = skills != null ? new HashSet<>(skills) : new HashSet<>();
        this.resumeText = resumeText != null ? resumeText : "";
        this.aptitudeScore = aptitudeScore;
        this.placed = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getCgpa() { return cgpa; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }

    public Set<String> getSkills() { return Collections.unmodifiableSet(skills); }
    public void setSkills(Set<String> skills) {
        this.skills = skills != null ? new HashSet<>(skills) : new HashSet<>();
    }
    public void addSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty()) {
            this.skills.add(skill.trim());
        }
    }

    public String getResumeText() { return resumeText; }
    public void setResumeText(String resumeText) { this.resumeText = resumeText; }

    public int getAptitudeScore() { return aptitudeScore; }
    public void setAptitudeScore(int aptitudeScore) { this.aptitudeScore = aptitudeScore; }

    public boolean isPlaced() { return placed; }
    public void setPlaced(boolean placed) { this.placed = placed; }

    public String getSkillsCsv() {
        return String.join(", ", skills);
    }

    /**
     * Checks if this student has all the required skills (HashSet subset check).
     */
    public boolean hasAllSkills(Set<String> required) {
        if (required == null || required.isEmpty()) return true;
        Set<String> lowerStudentSkills = new HashSet<>();
        for (String s : skills) lowerStudentSkills.add(s.toLowerCase());
        for (String req : required) {
            if (!lowerStudentSkills.contains(req.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes skill overlap count.
     */
    public int countMatchingSkills(Set<String> required) {
        if (required == null || required.isEmpty()) return 0;
        int count = 0;
        Set<String> lowerStudentSkills = new HashSet<>();
        for (String s : skills) lowerStudentSkills.add(s.toLowerCase());
        for (String req : required) {
            if (lowerStudentSkills.contains(req.toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates composite score for priority ranking.
     * 50% CGPA (normalized to 100), 30% Aptitude Score, 20% Skill Match percentage.
     */
    public double calculateRankingScore(Set<String> targetSkills) {
        double cgpaPart = (cgpa / 10.0) * 50.0;
        double aptPart = (aptitudeScore / 100.0) * 30.0;
        double skillRatio = (targetSkills == null || targetSkills.isEmpty()) ? 1.0 :
                (double) countMatchingSkills(targetSkills) / targetSkills.size();
        double skillPart = skillRatio * 20.0;
        return cgpaPart + aptPart + skillPart;
    }

    @Override
    public int compareTo(Student o) {
        // Natural ordering by CGPA descending, then aptitude score
        int cmp = Double.compare(o.cgpa, this.cgpa);
        if (cmp != 0) return cmp;
        return Integer.compare(o.aptitudeScore, this.aptitudeScore);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | CGPA: %.2f | Apt: %d | Skills: %s",
                rollNo, name, cgpa, aptitudeScore, getSkillsCsv());
    }
}
