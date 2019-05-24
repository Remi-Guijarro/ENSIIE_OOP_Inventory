package inventory.Stocks;

import inventory.Equipment;

import java.util.ArrayList;

public class Inventory extends AbstractStock {
    private ArrayList<SubStock> subStocks;

    public Inventory() {
        super();
        subStocks = new ArrayList<>();
    }

    public void createSubStock(ArrayList<Equipment> equipments, String emplacement) {
        SubStock s = new SubStock(emplacement);
        s.addEquipments(equipments);
        subStocks.add(s);
    }
}
