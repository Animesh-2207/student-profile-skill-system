-- Run this file in MySQL Workbench.
CREATE DATABASE IF NOT EXISTS student_management;
USE student_management;

-- Spring Boot + Hibernate will create/update these tables automatically
-- because spring.jpa.hibernate.ddl-auto=update is configured.
-- The equivalent table structure is shown here for learning/reference:
CREATE TABLE IF NOT EXISTS students (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(30) NOT NULL UNIQUE,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    course_name VARCHAR(100) NOT NULL,
    programming_score DOUBLE NOT NULL,
    mathematics_score DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS student_technical_skills (
    student_id BIGINT NOT NULL,
    skill_name VARCHAR(80) NOT NULL,
    CONSTRAINT fk_student_skills FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- Optional sample records (run after the Spring Boot application has created the tables).
INSERT INTO students (student_id, student_name, email, course_name, programming_score, mathematics_score)
VALUES
('STU101', 'Aarav Sharma', 'aarav@example.com', 'B.Tech CSIT', 88, 92),
('STU102', 'Priya Verma', 'priya@example.com', 'B.Tech CSE', 78, 85);

INSERT INTO student_technical_skills (student_id, skill_name)
SELECT id, 'Java' FROM students WHERE student_id = 'STU101';
INSERT INTO student_technical_skills (student_id, skill_name)
SELECT id, 'Spring Boot' FROM students WHERE student_id = 'STU101';
INSERT INTO student_technical_skills (student_id, skill_name)
SELECT id, 'Python' FROM students WHERE student_id = 'STU102';

-- Useful SQL queries
SELECT * FROM students;
SELECT * FROM student_technical_skills;
SELECT s.*, (s.programming_score + s.mathematics_score) / 2 AS average_score
FROM students s ORDER BY average_score DESC;
SELECT s.* FROM students s JOIN student_technical_skills sk ON s.id = sk.student_id
WHERE LOWER(sk.skill_name) = LOWER('Java');
SELECT * FROM students WHERE LOWER(course_name) = LOWER('B.Tech CSIT');
