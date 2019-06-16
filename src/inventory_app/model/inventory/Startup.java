package inventory_app.model.inventory;

import java.io.Serializable;

public class Startup implements Borrower,Serializable {
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

    public String getIncubatorName(){return this.incubator.getName();}
}