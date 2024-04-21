package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService service;

    private final FacultyRepository facultyRepository;

    public FacultyController(FacultyService service, FacultyRepository facultyRepository) {
        this.service = service;
        this.facultyRepository = facultyRepository;
    }

    @GetMapping
    public Faculty get(@RequestParam long id) {
        return service.get(id).getBody();
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return service.add(faculty).getBody();
    }

    @PutMapping("/{id}")
    public Faculty update(@RequestBody Faculty faculty, @PathVariable Long id) {
        return service.update(faculty, id).getBody();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> delete(@PathVariable long id) {

        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Faculty faculty = optionalFaculty.get();
        service.delete(faculty, id);
        return ResponseEntity.ok(faculty);
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