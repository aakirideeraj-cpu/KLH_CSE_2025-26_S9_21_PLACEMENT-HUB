-- ============================================
-- PLACEMENT HUB DATABASE
-- ============================================

DROP DATABASE IF EXISTS placementhub;

CREATE DATABASE placementhub;

USE placementhub;


-- ============================================
-- STUDENTS TABLE
-- ============================================

CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    cgpa DECIMAL(3,2) NOT NULL,
    skills VARCHAR(500)
);


-- ============================================
-- COMPANIES TABLE
-- ============================================

CREATE TABLE companies (
    company_id INT PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(100) NOT NULL,
    cgpa_cutoff DECIMAL(3,2) NOT NULL,
    required_skills VARCHAR(500)
);


-- ============================================
-- STUDENT DATA
-- ============================================

INSERT INTO students (name, cgpa, skills) VALUES
('Deeraj', 8.50, 'Java, SQL, Python'),
('Rahul', 7.80, 'Java, HTML, CSS'),
('Yeswant', 9.10, 'Python, SQL, JavaScript'),
('Chaitanya', 9.20, 'Java, SQL, HTML'),
('Arjun Reddy', 8.70, 'Java, SQL, React'),
('Sneha Rao', 9.30, 'Python, SQL, Machine Learning'),
('Varun Kumar', 7.60, 'Java, HTML, CSS'),
('Priya Sharma', 8.90, 'Python, Java, SQL'),
('Aditya Verma', 7.40, 'C++, Java, Data Structures'),
('Kavya Nair', 8.10, 'JavaScript, React, SQL'),
('Rohit Singh', 9.00, 'Java, Python, SQL'),
('Meghana Reddy', 8.30, 'Python, HTML, CSS');


-- ============================================
-- COMPANY DATA
-- ============================================

INSERT INTO companies (company_name, cgpa_cutoff, required_skills) VALUES
('TCS', 7.50, 'Java, SQL'),
('Infosys', 8.00, 'Python, SQL'),
('Accenture', 7.00, 'Java, HTML'),
('Wipro', 8.50, 'Java, SQL, Python'),
('Amazon', 8.50, 'Java, Python, SQL'),
('Microsoft', 8.00, 'C++, Java, Data Structures'),
('Deloitte', 7.50, 'Python, SQL, Communication'),
('Cognizant', 7.00, 'Java, SQL, HTML'),
('Capgemini', 7.50, 'Java, Python, SQL'),
('HCL Technologies', 7.00, 'Java, C++, SQL');


-- ============================================
-- VERIFY DATA
-- ============================================

SELECT * FROM students;

SELECT * FROM companies;