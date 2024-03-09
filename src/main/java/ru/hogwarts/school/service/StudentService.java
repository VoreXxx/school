package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //Add(POST)
    public Student add(Student student) {
        return studentRepository.save(student);
    }

    //Get(GET)
    public Student get(long id) {
        return studentRepository.findById(id).get();
    }

    //Update(PUT)
    public Student update(Student student, Long id) {
        studentRepository.findById(id);
        return studentRepository.save(student);
    }

    //Delete(DELETE)
    public void delete(long id) {
        studentRepository.deleteById(id);
    }

    //Filter
    public List<Student> filterByAge(int age) {
        return studentRepository.filterByAge(age);

    }
}
