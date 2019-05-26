package users;

import inventory.Borrower;
import inventory.Equipment;
import inventory.InventoryManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;

public class Student extends People implements Borrower,Serializable {

    public enum Grade {_1A, _2A, JIN;}

    private Grade grade;

    public Student(Student st){
        super(st.getId(),st.getFirstName(),st.getSurname(),st.getAddress(),st.getPhoneNumber(),st.getEmail());
        this.grade = st.grade;
    }

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

}
