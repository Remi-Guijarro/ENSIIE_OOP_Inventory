package users;

import inventory.Borrower;
import inventory.Course;
import inventory.Equipment;

import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

public class Teacher extends People implements Borrower {
    private ArrayList<Course> classes;

    public Teacher(String socialSecurityNumber,
                   String firstName, String surname,
                   String address, String phoneNumber, String email){
        super(socialSecurityNumber,firstName,surname,address,phoneNumber,email);
    }

    public void addCourse(Course course) {
        classes.add(course);
    }

    @Override
    public void borrow(Equipment equipment, Date borrowDate, String reason) throws NoSuchElementException {
        //TODO
    }

    @Override
    public void borrow(ArrayList<Equipment> equipment, Date borrowDate, String reason) throws NoSuchElementException {
        //TODO
    }

    @Override
    public ArrayList<Equipment> getBorrowedEquipment() {
        //TODO
        return null;
    }

    @Override
    public void returnEquipment(Equipment equipment) throws NoSuchElementException {
        //TODO
    }

    @Override
    public void returnEquipments(ArrayList<Equipment> equipments) throws NoSuchElementException {
        //TODO
    }
}
