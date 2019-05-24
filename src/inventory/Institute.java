package inventory;

import java.util.ArrayList;
import java.util.List;

public abstract class Institute {
    private final String id;
    private String name;
    private static int lastId = 0;
    private List<Equipment> equipments;

    public Institute(String name) {
        this.name = name;
        id = "institute_" + (++lastId);
        equipments = new ArrayList<>();
    }

    public void addEquipment(Equipment e) {
        equipments.add(e);
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
}
