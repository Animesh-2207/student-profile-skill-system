package com.example.studentmanagement.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false, unique = true, length = 30)
    private String studentId;

    @Column(name = "student_name", nullable = false, length = 100)
    private String studentName;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

    @Column(name = "programming_score", nullable = false)
    private double programmingScore;

    @Column(name = "mathematics_score", nullable = false)
    private double mathematicsScore;

    @ElementCollection
    @CollectionTable(name = "student_technical_skills", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "skill_name", nullable = false, length = 80)
    private List<String> technicalSkills = new ArrayList<>();

    public Student() {}

    public Student(String studentId, String studentName, String email, String courseName,
                   double programmingScore, double mathematicsScore, List<String> technicalSkills) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.courseName = courseName;
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        setTechnicalSkills(technicalSkills);
    }

    public Long getId() { return id; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public double getProgrammingScore() { return programmingScore; }
    public void setProgrammingScore(double programmingScore) { this.programmingScore = programmingScore; }
    public double getMathematicsScore() { return mathematicsScore; }
    public void setMathematicsScore(double mathematicsScore) { this.mathematicsScore = mathematicsScore; }
    public List<String> getTechnicalSkills() { return technicalSkills; }
    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills == null ? new ArrayList<>() : new ArrayList<>(technicalSkills);
    }

    @Transient
    public double getAverageScore() { return (programmingScore + mathematicsScore) / 2.0; }

    @Transient
    public String getGrade() {
        double average = getAverageScore();
        if (average >= 90) return "A+";
        if (average >= 80) return "A";
        if (average >= 70) return "B";
        if (average >= 60) return "C";
        if (average >= 50) return "D";
        return "Needs Improvement";
    }
}
