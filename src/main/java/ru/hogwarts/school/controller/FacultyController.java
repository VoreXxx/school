package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
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

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return service.add(faculty);
    }

    @PutMapping("/{id}")
    public Faculty update(@RequestBody Faculty faculty, @PathVariable Long id) {
        return service.update(faculty, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @GetMapping("/all")
    public Collection<Faculty> getAllFaculty() {
        return service.getAllFaculty();
    }

    @GetMapping("/filter/{color}")
    public Collection<Faculty> filterByColor(@RequestParam String color) {
        return service.filterByColor(color);
    }

    @GetMapping("/filter")
    public Collection<Faculty> getFacultyByNameOrColor
            (@RequestParam String name,
             @RequestParam String color) {
        return service.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);
    }
}