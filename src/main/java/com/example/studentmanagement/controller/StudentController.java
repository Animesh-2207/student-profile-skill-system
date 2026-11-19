package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@RequestBody Student student) { return service.create(student); }

    @GetMapping
    public List<Student> getAll() { return service.getAll(); }

    @GetMapping("/{studentId}")
    public Student getOne(@PathVariable String studentId) { return service.getByStudentId(studentId); }

    @PutMapping("/{studentId}")
    public Student update(@PathVariable String studentId, @RequestBody Student student) { return service.update(studentId, student); }

    @DeleteMapping("/{studentId}")
    public Map<String, String> delete(@PathVariable String studentId) {
        service.delete(studentId);
        return Map.of("message", "Student deleted successfully");
    }

    @PostMapping("/{studentId}/skills")
    public Student addSkill(@PathVariable String studentId, @RequestBody Map<String, String> body) {
        return service.addSkill(studentId, body.get("skill"));
    }

    @DeleteMapping("/{studentId}/skills")
    public Student removeSkill(@PathVariable String studentId, @RequestParam String skill) {
        return service.removeSkill(studentId, skill);
    }

    @GetMapping("/search/course/{courseName}")
    public List<Student> byCourse(@PathVariable String courseName) { return service.byCourse(courseName); }

    @GetMapping("/search/skill/{skill}")
    public List<Student> bySkill(@PathVariable String skill) { return service.bySkill(skill); }

    @GetMapping("/ranking/programming")
    public List<Student> topProgramming() { return service.topProgramming(); }

    @GetMapping("/ranking/mathematics")
    public List<Student> topMathematics() { return service.topMathematics(); }

    @GetMapping("/merit-list")
    public List<Student> getMeritList() {
    return service.getMeritList();
}
}
