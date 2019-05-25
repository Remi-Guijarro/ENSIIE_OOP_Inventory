package users;

import inventory.Borrower;
import inventory.Equipment;

import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

public class Student extends People implements Borrower {

    public enum Grade {_1A, _2A, JIN;}

    private Grade grade;
    public Student(String socialSecurityNumber,String firstName, String surname,
                   String address, String phoneNumber, String email,
                   Grade grade) {
        super(socialSecurityNumber,firstName,surname,address,phoneNumber,email);
        this.grade = grade;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
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
