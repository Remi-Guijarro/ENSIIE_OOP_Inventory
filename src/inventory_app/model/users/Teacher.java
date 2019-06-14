package inventory_app.model.users;

import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Course;

import java.io.Serializable;
import java.util.ArrayList;

public class Teacher extends People implements Borrower,Serializable {
    private ArrayList<Course> classes;

    public Teacher(String socialSecurityNumber,
                   String firstName, String surname,
                   String address, String phoneNumber, String email) {
        super(socialSecurityNumber, firstName, surname, address, phoneNumber, email);
    }

    public void addCourse(Course course) {
        classes.add(course);
    }

    @Override
    public String getName() {
        return this.getFirstName();
    }
}