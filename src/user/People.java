package user;

public abstract class People {
    private final String id;
    private String firstName;
    private String surName;
    private String adresse;
    private String phoneNumber;
    private String email;

    public People(String socialSecurityNumber,String firstName, String surName, String address, String phoneNumber, String email){
        this.id = socialSecurityNumber;
        this.firstName = firstName;
        this.surName = surName;
        this.adresse = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
