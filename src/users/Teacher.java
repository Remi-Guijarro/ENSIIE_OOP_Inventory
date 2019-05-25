package users;

import inventory.Borrower;
import inventory.Equipment;

import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

public class Teacher extends People implements Borrower {

    public Teacher(String socialSecurityNumber, String firstName, String surName, String address, String phoneNumber, String email){
        super(socialSecurityNumber,firstName,surName,address,phoneNumber,email);
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
