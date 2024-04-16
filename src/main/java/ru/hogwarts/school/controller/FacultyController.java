package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

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

    @GetMapping("/filterByColor")
    public Collection<Faculty> filterByColor(@RequestParam String color) {
        return service.filterByColor(color);
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return service.add(faculty);
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