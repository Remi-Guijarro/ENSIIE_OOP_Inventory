package users;

public class Student extends People implements Borrower {

    public enum Grade {_1A, _2A, JIN};

    private Grade grade;

    public Student(String socialSecurityNumber,String firstName, String surName, String address, String phoneNumber, String email,Grade grade){
        super(socialSecurityNumber,firstName,surName,address,phoneNumber,email);
        this.grade = grade;
    }

    /*public void borrow(Equipement equipement, Date borrowDate, Date returnDate, String reason) {

    }

    public List<Equipement> getBorrowedEquipement() {
        return null;
    }

    public void returnEquipement(Equipement equipement) {

    }

    public void borrow(AbstractStock<Equipement> equipements, Date borrowDate, Date returnDate, String reason) {

    }*/
}
