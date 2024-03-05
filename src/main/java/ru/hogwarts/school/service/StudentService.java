package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    //Map student's
    private final Map<Long, Student> storage = new HashMap<>();
    //Zero ID
    private long nextId = 0;

    //Add(POST)
    public Student add(Student student) {
        student.setId(++nextId);
        storage.put(student.getId(), student);
        return student;
    }

    //Get(GET)
    public Student get(long id) {
        return storage.get(id);
    }

    public Collection<Student> filterByAge(int age) {
        return storage.values()
                .stream()
                .filter(student -> student.getAge() == age)
                .toList();

    }

    //Update(PUT)
    public Student update(Student student) {
        if (storage.containsKey(student.getId())) {
            storage.put(student.getId(), student);
            return student;
        }
        return null;
    }

    //Delete(DELETE)
    public Student delete(long id) {
        return storage.remove(id);
    }
}
