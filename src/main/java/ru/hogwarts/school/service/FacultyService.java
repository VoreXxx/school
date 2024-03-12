package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    //Add(POST)
    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    //Get(GET)
    public Faculty get(long id) {
        return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }


    //Update(PUT)
    public Faculty update(Faculty faculty, Long id) {
        facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
        return facultyRepository.save(faculty);
    }

    //Delete(DELETE)
    public void delete(long id) {
        facultyRepository.deleteById(id);
    }

    //GetAllFaculty
    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    //Filter
    public Collection<Faculty> filterByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase
            (String name, String color) {
        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);
    }
}
