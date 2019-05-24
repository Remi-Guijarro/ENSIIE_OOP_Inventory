package user;

import java.util.Date;
import java.util.List;

public class Teacher extends People implements Borrower {

    public Teacher(String socialSecurityNumber, String firstName, String surName, String address, String phoneNumber, String email){
        super(socialSecurityNumber,firstName,surName,address,phoneNumber,email);
    }

  /*  public void borrow(Equipement equipement, Date borrowDate, Date returnDate, String reason) {

    }

    public List<Equipement> getBorrowedEquipement() {
        return null;
    }

    public void returnEquipement(Equipement equipement) {

    }

    public void borrow(AbstractStock<Equipement> equipements, Date borrowDate, Date returnDate, String reason) {

    }
    */
}
