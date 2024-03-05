package ru.hogwarts.school.controller;

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

    @GetMapping
    public Student get(@RequestParam long id) {
        return service.get(id);
    }

    @GetMapping("/filterByAge")
    public Collection<Student> filterStudentByAge(@RequestParam int age) {
        return service.filterByAge(age);
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
       return service.add(student);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
       return service.update(student);
    }

    @DeleteMapping
    public Student delete(@RequestParam long id) {
        return service.delete(id);
    }
}
