package inventory;

import users.Student;
import users.Teacher;

import java.util.ArrayList;

public class Course {
    private final String id;
    private String name;
    private ArrayList<Equipment> equipments;
    private ArrayList<Student> students;
    private Teacher teacher;
    private static ArrayList<String> knownIds;

    public Course(String id, String name,
                  ArrayList<Student> students, Teacher teacher,
                  ArrayList<Equipment> equipments) {
        if (knownIds.contains(id))
            throw new IllegalArgumentException("The ID already exists.");

        knownIds.add(id);
        this.id = id;
        this.name = name;
        this.equipments = equipments;
        this.students = students;
        this.teacher = teacher;
    }
}
