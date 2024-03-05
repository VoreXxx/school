package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    //Map student's
    private final Map<Long, Faculty> storage = new HashMap<>();
    //Zero ID
    private long nextId = 0;

    //Add(POST)
    public Faculty add(Faculty faculty) {
        faculty.setId(++nextId);
        storage.put(faculty.getId(), faculty);
        return faculty;
    }

    //Get(GET)
    public Faculty get(long id) {
        return storage.get(id);
    }

    //Filter
    public Collection<Faculty> filterByColor(String color) {
        return storage.values()
                .stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .toList();

    }

    //Update(PUT)
    public Faculty update(Faculty faculty) {
        if (storage.containsKey(faculty.getId())) {
            storage.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    //Delete(DELETE)
    public Faculty delete(long id) {
        return storage.remove(id);
    }
}
