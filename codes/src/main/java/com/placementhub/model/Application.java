package com.placementhub.model;

/**
 * Represents an Application linking a Student and a Company Recruitment Drive.
 */
public class Application {
    private int id;
    private int studentId;
    private int driveId;
    private String studentName;
    private String companyName;
    private double matchScore;
    private String status; // APPLIED, SHORTLISTED, INTERVIEW_SCHEDULED, REJECTED, PLACED
    private String interviewSlot;

    public Application() {}

    public Application(int id, int studentId, int driveId, String studentName,
                       String companyName, double matchScore, String status, String interviewSlot) {
        this.id = id;
        this.studentId = studentId;
        this.driveId = driveId;
        this.studentName = studentName;
        this.companyName = companyName;
        this.matchScore = matchScore;
        this.status = status;
        this.interviewSlot = interviewSlot;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getDriveId() { return driveId; }
    public void setDriveId(int driveId) { this.driveId = driveId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public double getMatchScore() { return matchScore; }
    public void setMatchScore(double matchScore) { this.matchScore = matchScore; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getInterviewSlot() { return interviewSlot; }
    public void setInterviewSlot(String interviewSlot) { this.interviewSlot = interviewSlot; }
}
