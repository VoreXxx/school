package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    //Map student's
    private final Map<Long, Student> storage = new HashMap<>();
    //Zero ID
    private long nextId = 0;

    //Add(POST)
    public void add(Student student) {
        storage.put(++nextId, student);
    }

    //Get(GET)
    public Student get(long id) {
        return storage.get(id);
    }

    //Update(PUT)
    public void update(Student student) {
        if (storage.containsKey(student.getId())) {
            storage.put(student.getId(), student);
        }
    }

    //Delete(DELETE)
    public void delete(long id) {
        storage.remove(id);
    }
}
