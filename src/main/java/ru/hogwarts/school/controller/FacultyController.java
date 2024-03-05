package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @GetMapping
    public Faculty get(@RequestParam long id) {
        return service.get(id);
    }

    @PostMapping
    public void add(@RequestBody Faculty faculty) {
        service.add(faculty);
    }

    @PutMapping
    public Faculty update(@RequestBody Faculty faculty) {
        return service.update(faculty);
    }

    @DeleteMapping
    public Faculty delete(@RequestParam long id) {
        return service.delete(id);
    }
}