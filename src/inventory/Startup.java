package inventory;

import java.util.Date;
import java.util.List;

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
    public void borrow(Equipment equipment, Date borrowDate, Date returnDate, String reason) {
        //TODO
    }

    @Override
    public List<Equipment> getBorrowedEquipement() {
        //TODO
        return null;
    }

    @Override
    public void returnEquipement(Equipment equipment) {
        //TODO
    }
}
