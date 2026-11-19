package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(String studentId);
    boolean existsByStudentId(String studentId);
    boolean existsByEmail(String email);
    List<Student> findByCourseNameIgnoreCase(String courseName);
    List<Student> findDistinctByTechnicalSkillsIgnoreCase(String skill);
    List<Student> findAllByOrderByProgrammingScoreDesc();
    List<Student> findAllByOrderByMathematicsScoreDesc();
}
