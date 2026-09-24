package com.placementhub.db;

import com.placementhub.model.Application;
import com.placementhub.model.CompanyDrive;
import com.placementhub.model.Student;

import java.sql.*;
import java.util.*;

/**
 * Data Access Object (DAO) for CRUD operations on Students, Drives, and Applications.
 * Handles auto-seeding sample college recruitment dataset.
 */
public class PlacementDAO {
    private final DatabaseManager dbManager;

    public PlacementDAO() {
        this.dbManager = DatabaseManager.getInstance();
        seedSampleDataIfEmpty();
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY cgpa DESC";
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setRollNo(rs.getString("roll_no"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setCgpa(rs.getDouble("cgpa"));
                s.setResumeText(rs.getString("resume_text"));
                s.setAptitudeScore(rs.getInt("aptitude_score"));
                s.setPlaced(rs.getInt("placed") == 1);

                String skillsStr = rs.getString("skills");
                if (skillsStr != null && !skillsStr.trim().isEmpty()) {
                    for (String part : skillsStr.split(",")) {
                        s.addSkill(part.trim());
                    }
                }
                list.add(s);
            }
        } catch (SQLException e) {
            System.err.println("[PlacementDAO] Error fetching students: " + e.getMessage());
        }
        return list;
    }

    public boolean addStudent(Student s) {
        String sql = "INSERT INTO students (roll_no, name, email, cgpa, skills, resume_text, aptitude_score, placed) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, s.getRollNo());
            pstmt.setString(2, s.getName());
            pstmt.setString(3, s.getEmail());
            pstmt.setDouble(4, s.getCgpa());
            pstmt.setString(5, s.getSkillsCsv());
            pstmt.setString(6, s.getResumeText());
            pstmt.setInt(7, s.getAptitudeScore());
            pstmt.setInt(8, s.isPlaced() ? 1 : 0);
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) s.setId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("[PlacementDAO] Error adding student: " + e.getMessage());
        }
        return false;
    }

    public List<CompanyDrive> getAllDrives() {
        List<CompanyDrive> list = new ArrayList<>();
        String sql = "SELECT * FROM company_drives ORDER BY id DESC";
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CompanyDrive d = new CompanyDrive();
                d.setId(rs.getInt("id"));
                d.setDriveCode(rs.getString("drive_code"));
                d.setCompanyName(rs.getString("company_name"));
                d.setRoleTitle(rs.getString("role_title"));
                d.setMinCgpa(rs.getDouble("min_cgpa"));
                d.setMaxIntake(rs.getInt("max_intake"));
                d.setCtcLpa(rs.getDouble("ctc_lpa"));
                d.setDescription(rs.getString("description"));
                d.setInterviewDate(rs.getString("interview_date"));
                d.setStatus(rs.getString("status"));

                String skillsStr = rs.getString("required_skills");
                if (skillsStr != null && !skillsStr.trim().isEmpty()) {
                    for (String part : skillsStr.split(",")) {
                        d.addRequiredSkill(part.trim());
                    }
                }
                list.add(d);
            }
        } catch (SQLException e) {
            System.err.println("[PlacementDAO] Error fetching drives: " + e.getMessage());
        }
        return list;
    }

    public boolean addDrive(CompanyDrive d) {
        String sql = "INSERT INTO company_drives (drive_code, company_name, role_title, min_cgpa, required_skills, max_intake, ctc_lpa, description, interview_date, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, d.getDriveCode());
            pstmt.setString(2, d.getCompanyName());
            pstmt.setString(3, d.getRoleTitle());
            pstmt.setDouble(4, d.getMinCgpa());
            pstmt.setString(5, d.getRequiredSkillsCsv());
            pstmt.setInt(6, d.getMaxIntake());
            pstmt.setDouble(7, d.getCtcLpa());
            pstmt.setString(8, d.getDescription());
            pstmt.setString(9, d.getInterviewDate());
            pstmt.setString(10, d.getStatus());
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) d.setId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("[PlacementDAO] Error adding drive: " + e.getMessage());
        }
        return false;
    }

    public List<Application> getAllApplications() {
        List<Application> list = new ArrayList<>();
        String sql = "SELECT * FROM applications ORDER BY id DESC";
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Application a = new Application();
                a.setId(rs.getInt("id"));
                a.setStudentId(rs.getInt("student_id"));
                a.setDriveId(rs.getInt("drive_id"));
                a.setStudentName(rs.getString("student_name"));
                a.setCompanyName(rs.getString("company_name"));
                a.setMatchScore(rs.getDouble("match_score"));
                a.setStatus(rs.getString("status"));
                a.setInterviewSlot(rs.getString("interview_slot"));
                list.add(a);
            }
        } catch (SQLException e) {
            System.err.println("[PlacementDAO] Error fetching applications: " + e.getMessage());
        }
        return list;
    }

    public boolean addApplication(Application a) {
        String sql = "INSERT INTO applications (student_id, drive_id, student_name, company_name, match_score, status, interview_slot) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, a.getStudentId());
            pstmt.setInt(2, a.getDriveId());
            pstmt.setString(3, a.getStudentName());
            pstmt.setString(4, a.getCompanyName());
            pstmt.setDouble(5, a.getMatchScore());
            pstmt.setString(6, a.getStatus());
            pstmt.setString(7, a.getInterviewSlot());
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) a.setId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("[PlacementDAO] Error adding application: " + e.getMessage());
        }
        return false;
    }

    private void seedSampleDataIfEmpty() {
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM students")) {
            if (rs.next() && rs.getInt(1) > 0) {
                return; // already seeded
            }
        } catch (SQLException e) {
            System.err.println("[PlacementDAO] Error checking student count: " + e.getMessage());
            return;
        }

        System.out.println("[PlacementDAO] Seeding realistic campus recruitment dataset...");

        // Seed Drives
        List<CompanyDrive> sampleDrives = List.of(
                new CompanyDrive(0, "DRV-GGL-01", "Google", "Software Engineer - Backend & Cloud", 8.5,
                        Set.of("Java", "Distributed Systems", "Algorithms", "System Design"),
                        3, 34.5, "Looking for high-impact engineers skilled in scalable backend architecture, graph algorithms, and distributed caching.", "2026-10-10"),
                new CompanyDrive(0, "DRV-MSFT-02", "Microsoft", "FullStack SDE", 8.0,
                        Set.of("Java", "TypeScript", "SQL", "Cloud"),
                        4, 28.0, "Core engineering team developing Azure services, microservices, and React/TypeScript frontends.", "2026-10-12"),
                new CompanyDrive(0, "DRV-AMZN-03", "Amazon", "SDE 1 - Logistics Systems", 7.8,
                        Set.of("Java", "Data Structures", "OOP", "AWS"),
                        5, 30.0, "Design and maintain high-throughput fulfillment engines with tight latency and high availability constraints.", "2026-10-14"),
                new CompanyDrive(0, "DRV-CSCO-04", "Cisco Systems", "Network Software Engineer", 7.5,
                        Set.of("C++", "Networking", "Python", "OS"),
                        3, 19.5, "Next-gen network protocol development, kernel optimization, and telemetry data processing.", "2026-10-16"),
                new CompanyDrive(0, "DRV-TCS-05", "TCS Digital", "Systems Developer", 6.8,
                        Set.of("Java", "SQL", "Problem Solving"),
                        8, 8.5, "Enterprise solution engineering across banking, healthcare, and retail sectors.", "2026-10-18"),
                new CompanyDrive(0, "DRV-INFY-06", "Infosys PP", "Power Programmer (Specialist)", 7.0,
                        Set.of("Python", "Java", "Algorithms", "Git"),
                        6, 9.5, "High-intensity development role focusing on algorithmic solutions and AI integrations.", "2026-10-20")
        );

        for (CompanyDrive d : sampleDrives) {
            addDrive(d);
        }

        // Seed Students
        List<Student> sampleStudents = List.of(
                new Student(0, "22KLH001", "Aarav Sharma", "aarav@klh.edu", 9.2,
                        Set.of("Java", "Distributed Systems", "Algorithms", "System Design", "AWS", "SQL"),
                        "Passionate backend developer with experience building distributed key-value stores. Proficient in Java, Distributed Systems, Algorithms, and System Design.", 95),
                new Student(0, "22KLH002", "Ananya Reddy", "ananya@klh.edu", 8.9,
                        Set.of("Java", "TypeScript", "SQL", "Cloud", "Algorithms"),
                        "Fullstack enthusiast with expertise in Spring Boot, React, and Azure Cloud microservices. Strong foundation in Data Structures and SQL.", 91),
                new Student(0, "22KLH003", "Rohan Verma", "rohan@klh.edu", 8.4,
                        Set.of("Java", "Data Structures", "OOP", "AWS", "Python"),
                        "Experienced with microservices and AWS Lambda. Built an e-commerce high-throughput catalog system using Java, Data Structures, and OOP.", 88),
                new Student(0, "22KLH004", "Sneha Patel", "sneha@klh.edu", 8.1,
                        Set.of("C++", "Networking", "Python", "OS", "Linux"),
                        "Low-level systems enthusiast with strong knowledge of TCP/IP, Linux socket programming, OS internals, and high-performance C++.", 85),
                new Student(0, "22KLH005", "Vikram Rao", "vikram@klh.edu", 7.9,
                        Set.of("Java", "SQL", "Problem Solving", "Git"),
                        "Detail-oriented software student skilled in database design, Java OOP, and algorithms. Solved 350+ LeetCode problems.", 82),
                new Student(0, "22KLH006", "Pooja Hegde", "pooja@klh.edu", 8.7,
                        Set.of("Java", "Python", "Algorithms", "Git", "Cloud"),
                        "Winner of national level college hackathon. Built predictive analytics pipelines with Python, Java, and Cloud technologies.", 89),
                new Student(0, "22KLH007", "Karthik Nair", "karthik@klh.edu", 7.3,
                        Set.of("Java", "SQL", "Problem Solving"),
                        "Strong focus on relational database optimization, Java backend services, and agile software development lifecycle.", 78),
                new Student(0, "22KLH008", "Meera Iyer", "meera@klh.edu", 7.6,
                        Set.of("Python", "Java", "Algorithms", "Git", "SQL"),
                        "Specialized in backend APIs and data automation scripts using Python and Java. High aptitude in logical reasoning.", 80),
                new Student(0, "22KLH009", "Devansh Joshi", "devansh@klh.edu", 8.6,
                        Set.of("Java", "Distributed Systems", "Algorithms", "System Design"),
                        "Designed an end-to-end consensus protocol simulator in Java. Deep interest in distributed databases and concurrency.", 93),
                new Student(0, "22KLH010", "Ishita Sen", "ishita@klh.edu", 6.9,
                        Set.of("Java", "SQL", "Problem Solving"),
                        "Proficient in Java desktop and enterprise application development, MySQL database query optimization.", 74),
                new Student(0, "22KLH011", "Aditya Kulkarni", "aditya@klh.edu", 8.2,
                        Set.of("Java", "TypeScript", "SQL", "Cloud"),
                        "Cloud native developer experienced with containerization (Docker, Kubernetes) and enterprise cloud APIs.", 86),
                new Student(0, "22KLH012", "Rhea Kapoor", "rhea@klh.edu", 7.5,
                        Set.of("C++", "Networking", "Python", "OS"),
                        "Contributed to open source networking tools. Skilled in network flow analysis, multi-threading, and system programming.", 81)
        );

        for (Student s : sampleStudents) {
            addStudent(s);
        }

        System.out.println("[PlacementDAO] Sample data seeded successfully (6 drives, 12 students).");
    }
}
