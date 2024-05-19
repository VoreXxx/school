package ru.hogwarts.school.service;

import liquibase.pro.packaged.S;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //Add(POST)
    public Student add(Student student) {
        logger.info("Adding student: {}", student);
        Student addedStudent = studentRepository.save(student);
        logger.debug("Student added successfully: {}", addedStudent);

        return addedStudent;
    }


    //Get(GET)
    public Student get(long id) {
        logger.info("Finding student with id: {}", id);
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        logger.debug("Found student: {}", student);

        return student;
    }


    //Update(PUT)
    public Student update(Student student, Long id) {
        logger.info("Updating student with id: {}", id);

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Student with id {} not found", id);
                    return new StudentNotFoundException();
                });

        existingStudent.setId(student.getId());
        Student updatedStudent = studentRepository.save(existingStudent);

        logger.debug("Updated student: {}", updatedStudent);

        return updatedStudent;
    }


    //Delete(DELETE)
    public void delete(long id) {
        logger.info("Deleting student with id: {}", id);

        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            logger.info("Student with id {} has been deleted", id);
        } else {
            logger.error("Student with id {} not found, unable to delete", id);
            throw new StudentNotFoundException();
        }
    }


    //GetAllStudents
    public Collection<Student> getAllStudents() {
        logger.info("Retrieving all students from the database");

        Collection<Student> students = studentRepository.findAll();

        if (students.isEmpty()) {
            logger.warn("No students found in the database");
        } else {
            logger.info("Retrieved {} students from the database", students.size());
        }

        return students;
    }


    //Filter
    public Collection<Student> filterByAge(int age) {
        logger.info("Filtering students by age: {}", age);

        Collection<Student> filteredStudents = studentRepository.findByAge(age);

        if (filteredStudents.isEmpty()) {
            logger.warn("No students found with age: {}", age);
        } else {
            logger.info("Filtered {} students with age: {}", filteredStudents.size(), age);
        }

        return filteredStudents;
    }


    public Collection<Student> filterByAgeMinMax(Integer minAge, Integer maxAge) {
        logger.info("Filtering students by age between {} and {}", minAge, maxAge);

        Collection<Student> filteredStudents = studentRepository.findByAgeBetween(minAge, maxAge);

        if (filteredStudents.isEmpty()) {
            logger.warn("No students found with age between {} and {}", minAge, maxAge);
        } else {
            logger.info("Filtered {} students with age between {} and {}", filteredStudents.size(), minAge, maxAge);
        }

        return filteredStudents;
    }


    public Student getById(Long studentId) {
        logger.info("Getting student by id: {}", studentId);

        Student student = studentRepository.getById(studentId);

        if (student == null) {
            logger.warn("Student with id {} not found", studentId);
        } else {
            logger.info("Retrieved student: {}", student);
        }

        return student;
    }

    public Collection<String> getFilteredByName() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(string -> string.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    public Double getAllStudentsAvgAge() {
        return studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    public void getStudentNames() {
        Thread thread1 = new Thread(() -> {
            printName(3L);
            printName(4L);
        });
        thread1.setName("Thread 1");

        Thread thread2 = new Thread(() -> {
            printName(5L);
            printName(6L);
        });
        thread2.setName("Thread 2");

        thread1.start();
        thread2.start();

        printName(1L);
        printName(2L);
    }

    private void printName(Long id) {
        String studentname = studentRepository.getById(id).getName();
        System.out.println(studentname);
    }

    public void getStudentNamesSyns() {
        Thread thread1 = new Thread(() -> {
            printNameSyns(3L);
            printNameSyns(4L);
        });

        Thread thread2 = new Thread(() -> {
            printNameSyns(5L);
            printNameSyns(6L);
        });
        printNameSyns(1L);
        printNameSyns(2L);
        thread1.start();
        thread2.start();


    }

    private synchronized void printNameSyns(Long id) {
        String studentname = studentRepository.getById(id).getName();
        System.out.println(studentname);
    }
}
