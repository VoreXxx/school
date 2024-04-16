package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        Student student = service.get(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
        return service.add(student);
    }

    @PutMapping("/{id}")
    public Student update(@RequestBody Student student, @PathVariable Long id) {
        return service.update(student, id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public Collection<Student> getAllStudent() {
        return service.getAllStudents();
    }

    @GetMapping("/filterByAge")
    public Collection<Student> filterByAge(@RequestParam int age) {
        return service.filterByAge(age);
    }

    @GetMapping("/filterByAgeRange")
    public Collection<Student> filterByAgeBetween(@RequestParam Integer minAge,
                                                  @RequestParam Integer maxAge) {
        return service.filterByAgeMinMax(minAge, maxAge);
    }
}
