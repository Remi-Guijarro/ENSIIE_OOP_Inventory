package inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class Startup implements Borrower {
    private final String id;
    private String name;
    private final Incubator incubator;

    public Startup(String name, String SIREN, Incubator incubator) {
        this.name = name;
        id = SIREN;
        this.incubator = incubator;
    }

    public String getSIREN() {
        return id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
