package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Comparator;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) { this.repository = repository; }

    public Student create(Student student) {
        validate(student);
        if (repository.existsByStudentId(student.getStudentId()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Student ID already exists");
        if (repository.existsByEmail(student.getEmail()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        return repository.save(student);
    }

    public List<Student> getAll() { return repository.findAll(); }

    public Student getByStudentId(String studentId) {
        return repository.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    public Student update(String studentId, Student updated) {
        Student existing = getByStudentId(studentId);
        validate(updated);
        if (!existing.getEmail().equalsIgnoreCase(updated.getEmail()) && repository.existsByEmail(updated.getEmail()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        existing.setStudentName(updated.getStudentName());
        existing.setEmail(updated.getEmail());
        existing.setCourseName(updated.getCourseName());
        existing.setProgrammingScore(updated.getProgrammingScore());
        existing.setMathematicsScore(updated.getMathematicsScore());
        existing.setTechnicalSkills(updated.getTechnicalSkills());
        return repository.save(existing);
    }

    public void delete(String studentId) { repository.delete(getByStudentId(studentId)); }

    public Student addSkill(String studentId, String skill) {
        Student student = getByStudentId(studentId);
        String cleaned = clean(skill);
        boolean alreadyPresent = student.getTechnicalSkills().stream().anyMatch(s -> s.equalsIgnoreCase(cleaned));
        if (!alreadyPresent) student.getTechnicalSkills().add(cleaned);
        return repository.save(student);
    }

    public Student removeSkill(String studentId, String skill) {
        Student student = getByStudentId(studentId);
        student.getTechnicalSkills().removeIf(s -> s.equalsIgnoreCase(skill.trim()));
        return repository.save(student);
    }

    public List<Student> byCourse(String course) { return repository.findByCourseNameIgnoreCase(course); }
    public List<Student> bySkill(String skill) { return repository.findDistinctByTechnicalSkillsIgnoreCase(skill); }
    public List<Student> topProgramming() { return repository.findAllByOrderByProgrammingScoreDesc(); }
    public List<Student> topMathematics() { return repository.findAllByOrderByMathematicsScoreDesc(); }

    private void validate(Student s) {
        if (s.getStudentId() == null || s.getStudentId().isBlank() || s.getStudentName() == null || s.getStudentName().isBlank()
                || s.getEmail() == null || s.getEmail().isBlank() || s.getCourseName() == null || s.getCourseName().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student ID, name, email and course are required");
        if (!s.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enter a valid email address");
        if (s.getProgrammingScore() < 0 || s.getProgrammingScore() > 100 || s.getMathematicsScore() < 0 || s.getMathematicsScore() > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Scores must be between 0 and 100");
        if (s.getTechnicalSkills() != null) {
            List<String> cleaned = s.getTechnicalSkills().stream().filter(x -> x != null && !x.isBlank()).map(String::trim).distinct().toList();
            s.setTechnicalSkills(cleaned);
        }
    }
    private String clean(String value) {
        if (value == null || value.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Skill cannot be empty");
        return value.trim();
    }
    public List<Student> getMeritList() {

    return repository.findAll()
        .stream()
        .sorted(
            Comparator.comparingDouble(
                (Student s) ->
                    (s.getProgrammingScore()
                    + s.getMathematicsScore()) / 2.0
            ).reversed()
        )
        .toList();
}
}
