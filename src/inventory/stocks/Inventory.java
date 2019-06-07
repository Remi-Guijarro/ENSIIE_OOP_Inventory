package inventory.stocks;

import inventory.Equipment;
import inventory.InventoryManager;

import java.io.Serializable;
import java.util.ArrayList;

public class Inventory extends AbstractStock implements Serializable {
    private ArrayList<SubStock> subStocks;
    private final static Inventory ourInstance = new Inventory();
    public static Inventory getInstance() {
        return ourInstance;
    }
    private Inventory() {
        super();
        subStocks = new ArrayList<>();
    }

    public void createSubStock(ArrayList<Equipment> equipments, String emplacement) {
        SubStock s = new SubStock(emplacement);
        s.addEquipments(equipments);
        subStocks.add(s);
    }

    public ArrayList<SubStock> getSubStocks() {
        return subStocks;
    }
}
