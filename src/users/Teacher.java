package users;

import inventory.Borrower;
import inventory.Course;
import inventory.Equipment;

import java.util.ArrayList;

public class Teacher extends People implements Borrower {
    private ArrayList<Course> classes;

    public Teacher(String socialSecurityNumber,
                   String firstName, String surname,
                   String address, String phoneNumber, String email) {
        super(socialSecurityNumber, firstName, surname, address, phoneNumber, email);
    }

    public void addCourse(Course course) {
        classes.add(course);
    }
}
