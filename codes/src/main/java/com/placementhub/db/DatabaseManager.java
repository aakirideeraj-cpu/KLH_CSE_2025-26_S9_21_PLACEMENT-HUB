package com.placementhub.db;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Manages database connectivity with dual-mode support:
 * 1. Primary: MySQL over JDBC as specified in project slides.
 * 2. Fallback: Embedded SQLite over JDBC for zero-setup instant execution.
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;
    private String databaseType = "UNKNOWN";

    private DatabaseManager() {
        initConnection();
        initTables();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void initConnection() {
        Properties props = new Properties();
        File propFile = new File("db.properties");
        if (propFile.exists()) {
            try (FileInputStream fis = new FileInputStream(propFile)) {
                props.load(fis);
            } catch (Exception e) {
                System.err.println("[DatabaseManager] Could not read db.properties: " + e.getMessage());
            }
        }

        String mysqlHost = props.getProperty("mysql.host", "localhost");
        String mysqlPort = props.getProperty("mysql.port", "3306");
        String mysqlDb = props.getProperty("mysql.database", "placement_hub");
        String mysqlUser = props.getProperty("mysql.user", "root");
        String mysqlPass = props.getProperty("mysql.password", "root");

        // Try MySQL First
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // First connect without DB to create database if not exists
            String serverUrl = "jdbc:mysql://" + mysqlHost + ":" + mysqlPort + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
            try (Connection serverConn = DriverManager.getConnection(serverUrl, mysqlUser, mysqlPass);
                 Statement stmt = serverConn.createStatement()) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + mysqlDb);
            }

            String fullUrl = "jdbc:mysql://" + mysqlHost + ":" + mysqlPort + "/" + mysqlDb + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(fullUrl, mysqlUser, mysqlPass);
            databaseType = "MySQL 8.0 (JDBC)";
            System.out.println("[DatabaseManager] Connected successfully to MySQL: " + fullUrl);
            return;
        } catch (Exception e) {
            System.out.println("[DatabaseManager] MySQL connection unavailable (" + e.getMessage() + ").");
            System.out.println("[DatabaseManager] Automatically falling back to SQLite JDBC for seamless offline execution.");
        }

        // Fallback to SQLite
        try {
            Class.forName("org.sqlite.JDBC");
            String sqliteUrl = "jdbc:sqlite:placement_hub.db";
            connection = DriverManager.getConnection(sqliteUrl);
            databaseType = "SQLite (Embedded JDBC)";
            System.out.println("[DatabaseManager] Connected to SQLite database: placement_hub.db");
        } catch (Exception e) {
            System.err.println("[DatabaseManager] Fatal error creating SQLite connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initTables() {
        if (connection == null) return;
        try (Statement stmt = connection.createStatement()) {
            boolean isMysql = databaseType.startsWith("MySQL");
            String autoIncrement = isMysql ? "AUTO_INCREMENT" : "AUTOINCREMENT";
            String pkInt = isMysql ? "INT PRIMARY KEY AUTO_INCREMENT" : "INTEGER PRIMARY KEY AUTOINCREMENT";

            // Students table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS students (" +
                    "id " + pkInt + ", " +
                    "roll_no VARCHAR(50) UNIQUE NOT NULL, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100), " +
                    "cgpa DOUBLE NOT NULL, " +
                    "skills TEXT NOT NULL, " +
                    "resume_text TEXT, " +
                    "aptitude_score INT DEFAULT 75, " +
                    "placed INT DEFAULT 0" +
                    ")");

            // Company Drives table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS company_drives (" +
                    "id " + pkInt + ", " +
                    "drive_code VARCHAR(50) UNIQUE NOT NULL, " +
                    "company_name VARCHAR(100) NOT NULL, " +
                    "role_title VARCHAR(100) NOT NULL, " +
                    "min_cgpa DOUBLE NOT NULL, " +
                    "required_skills TEXT NOT NULL, " +
                    "max_intake INT NOT NULL, " +
                    "ctc_lpa DOUBLE NOT NULL, " +
                    "description TEXT, " +
                    "interview_date VARCHAR(50), " +
                    "status VARCHAR(20) DEFAULT 'ACTIVE'" +
                    ")");

            // Applications table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS applications (" +
                    "id " + pkInt + ", " +
                    "student_id INT NOT NULL, " +
                    "drive_id INT NOT NULL, " +
                    "student_name VARCHAR(100), " +
                    "company_name VARCHAR(100), " +
                    "match_score DOUBLE DEFAULT 0.0, " +
                    "status VARCHAR(30) DEFAULT 'APPLIED', " +
                    "interview_slot VARCHAR(50)" +
                    ")");

            System.out.println("[DatabaseManager] Database tables initialized successfully.");
        } catch (SQLException e) {
            System.err.println("[DatabaseManager] Error creating tables: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                initConnection();
            }
        } catch (SQLException e) {
            initConnection();
        }
        return connection;
    }

    public String getDatabaseType() {
        return databaseType;
    }
}
