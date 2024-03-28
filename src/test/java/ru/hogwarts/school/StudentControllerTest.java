package ru.hogwarts.school;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    List<Student> savedStudents;

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        Student student = new Student(1L, "Harry Potter", 10);
        Student student2 = new Student(2L, "Ron Weasley", 11);
        List<Student> studentLists = List.of(student, student2);

        savedStudents = studentRepository.saveAll(studentLists);
    }

    @Test
    public void contextLoads() {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void testGetStudentInfo() throws JSONException, JsonProcessingException {
        String expected = mapper.writeValueAsString(savedStudents.get(0));
        Long studentId = savedStudents.get(0).getId();

        ResponseEntity<String> response =
                restTemplate.getForEntity("http://localhost:" + port + "/student/" + studentId,
                        String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void testGetStudents() {
         ResponseEntity<List<Student>> response = restTemplate.exchange(
                "http://localhost:" + port + "/student/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        List<Student> actualStudents = response.getBody().stream().toList();
        assertEquals(savedStudents, actualStudents);
    }

    @Test
    void createStudent() throws JsonProcessingException, JSONException {
        Student student = new Student(3L, "Neville Longbottom", 11);
        String expected = mapper.writeValueAsString(student);

        ResponseEntity<String> response = restTemplate.postForEntity("/student", student, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void editStudent() {
        Student student = new Student(5L, "Hermione Granger", 10);
        HttpEntity<Student> entity = new HttpEntity<>(student);
        student.setId(savedStudents.get(0).getId());

        ResponseEntity<Student> response =
                this.restTemplate
                        .exchange("/student/" + savedStudents.get(0).getId(), HttpMethod.PUT,
                                entity, Student.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(student, response.getBody());
    }

    @Test
    void deleteStudent() {
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<String> response =
                restTemplate
                        .exchange("/student/" + savedStudents.get(0).getId(), HttpMethod.DELETE,
                                entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
