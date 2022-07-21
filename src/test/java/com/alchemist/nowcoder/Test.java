package com.alchemist.nowcoder;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

public class Test {
    Map<Object,Object> map = new HashMap<>();

    public static void main(String[] args) {
        Student s = new Student("aaa", 18, "语文");
        System.out.println(s.getAge());
    }
}

class Student extends Person{
    String major;

    public Student(String name, int age, String major) {
        super.setAge(age);
        this.major = major;
    }

    @Override
    public String toString() {
        return "Student{" +
                "major='" + major + '\'' +
                '}';
    }
}

class Person {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
