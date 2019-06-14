package inventory_app.model.inventory;

import inventory_app.model.users.Student;
import inventory_app.model.users.Teacher;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private final String id;
    private String name;
    private ArrayList<inventory_app.model.inventory.Equipment> equipments;
    private ArrayList<Student> students;
    private Teacher teacher;
    private static ArrayList<String> knownIds = new ArrayList<>();

    public Course(String id, String name,
                  ArrayList<Student> students, Teacher teacher,
                  ArrayList<inventory_app.model.inventory.Equipment> equipments) {
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