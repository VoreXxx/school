package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public Student get(@RequestParam long id) {
        return service.get(id);
    }

    @PostMapping
    public void add(@RequestBody Student student) {
        service.add(student);
    }

    @PutMapping
    public void update(@RequestBody Student student) {
        service.update(student);
    }

    @DeleteMapping
    public void delete(@RequestParam long id) {
        service.delete(id);
    }
}
