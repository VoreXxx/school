package ru.hogwarts.school.model;

public class Student {
    private Long id;
    private String name;
    private int age;

    //constructor
    public Student(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }


    //getter's
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }


    //setter's
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
