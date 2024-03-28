package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student getById(Long id);
    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge);
}
