package users;

import inventory.Borrower;

import java.io.Serializable;

public abstract class People implements Serializable {
    private final String id;
    private String firstName;
    private String surname;
    private String address;
    private String phoneNumber;
    private String email;

    public People(People p){
        this.address = p.address;
        this.id = p.id;
        this.firstName = p.firstName;
        this.surname = p.surname;
        this.phoneNumber = p.phoneNumber;
        this.email = p.email;
    }

    public People(String socialSecurityNumber, String firstName, String surname,
                  String address, String phoneNumber, String email) {
        this.id = socialSecurityNumber;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
