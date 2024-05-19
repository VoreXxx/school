package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    //Add(POST)
    public ResponseEntity<Faculty> add(Faculty faculty) {
        logger.info("This method is added by the faculty");
        if (faculty == null) {
            logger.error("Can't create faculty, when faculty is null");
            return ResponseEntity.badRequest().build();
        }
        logger.debug("Was created {}", faculty);
        return ResponseEntity.ok(facultyRepository.save(faculty));
    }

    //Get(GET)
    public ResponseEntity<Faculty> get(long id) {
        logger.info("This method is find faculty by id={}", id);
        Optional<Faculty> byId = facultyRepository.findById(id);
        if (byId.isEmpty()) {
            logger.error("There is no faculty with id={}", id);
            return ResponseEntity.badRequest().build();
        }
        logger.debug("Faculty was founded by id={}", id);
        return ResponseEntity.ok(byId.get());
    }


    //Update(PUT)
    public ResponseEntity<Faculty> update(Faculty faculty, Long id) {
        logger.info("This method is update faculty");
        if (faculty == null) {
            logger.error("Can't update faculty, when faculty is null");
            return ResponseEntity.badRequest().build();
        }
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            logger.error("There is no faculty with your id");
            return ResponseEntity.notFound().build();
        }
        logger.debug("{} was updated", faculty);
        return ResponseEntity.ok(facultyRepository.save(faculty));
    }

    //Delete(DELETE)
    public ResponseEntity<Faculty> delete(Faculty faculty, long id) {
        logger.info("This method is delete faculty");

        if (facultyRepository.findById(id).isEmpty()) {
            logger.error("There is no faculty with your id");
            return ResponseEntity.notFound().build();
        }

        facultyRepository.deleteById(id);
        logger.debug("{} was deleted", faculty);

        return ResponseEntity.ok(faculty);
    }


    //GetAllFaculty
    public Collection<Faculty> getAllFaculty() {
        logger.info("Fetching all faculties");
        Collection<Faculty> allFaculties = facultyRepository.findAll();
        logger.debug("Retrieved {} faculties", allFaculties.size());
        return allFaculties;
    }


    //Filter
    public Collection<Faculty> filterByColor(String color) {
        logger.info("Fetching faculties with color: {}", color);
        Collection<Faculty> filteredFaculties = facultyRepository.findByColor(color);

        logger.debug("Filtered {} faculties by color: {}", filteredFaculties.size(), color);

        return filteredFaculties;
    }


    public Collection<Faculty> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase
            (String name, String color) {
        logger.info("Fetching faculties by name containing '{}' or color containing '{}'", name, color);
        Collection<Faculty> filteredFaculties =
                facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);

        logger.debug("Found {} faculties matching the criteria", filteredFaculties.size());

        return filteredFaculties;
    }

    public String getFacultyWithMaxNameLength() {
        logger.info("Was invoked method to find faculty name with max length");

        return facultyRepository
                .findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse("");
    }
}
