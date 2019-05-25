package inventory;

import inventory.stocks.Inventory;
import inventory.stocks.SubStock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class InventoryManager {
    private static InventoryManager ourInstance = new InventoryManager();
    private Inventory inventory;

    public static InventoryManager getInstance() {
        return ourInstance;
    }

    private InventoryManager() {
        inventory = new Inventory();
    }
    ////////////////////////////////////////////////////////////////

    public void addEquipment(Equipment equipment) {
        inventory.addEquipment(equipment);
    }

    public void addEquipments(ArrayList<Equipment> equipments) {
        inventory.addEquipments(equipments);
    }

    public void moveToSubStock(Equipment e, SubStock destination) throws NoSuchElementException {
        if (!getAll().contains(e))
            throw new NoSuchElementException("The equipment does not exist.");
        if (!inventory.getSubStocks().contains(destination))
            throw new NoSuchElementException("The substock does not exist.");

        Iterator<Equipment> inventoryIter = inventory.getEquipments().iterator();
        while (inventoryIter.hasNext()) {
            Equipment eq = inventoryIter.next();
            if (e.equals(eq)) {
                destination.addEquipment(
                        inventory.pop(eq.getReference())
                );
                return;
            }
        }

        ArrayList<SubStock> subStocks = inventory.getSubStocks();
        for(int i=0 ; i<subStocks.size() ; ++i) {
            SubStock current = subStocks.get(i);
            ArrayList<Equipment> equipments = current.getEquipments();
            for(int j=0 ; j<equipments.size() ; ++j) {
                Equipment equipment = equipments.get(j);
                if (e.equals(equipment)) {
                    destination.addEquipment(
                            current.pop(equipment.getReference())
                    );
                    return;
                }
            }
        }
    }

    public ArrayList<Equipment> getAll() {
        ArrayList<Equipment> equipments = new ArrayList<>(inventory.getEquipments());
        for (SubStock s : inventory.getSubStocks()) {
            equipments.addAll(s.getEquipments());
        }
        return equipments;
    }

    public ArrayList<Equipment> getBorrowed() {
        ArrayList<Equipment> equipements = new ArrayList<>();
        ArrayList<Borrowing> list = BorrowingsList.getInstance().getBorrowings();
        for (Borrowing b : list) {
            Borrowable borrowable = b.getBorrowable();
            if (borrowable instanceof Equipment) {
                equipements.add((Equipment) borrowable);
            }
        }
        return equipements;
    }

    public ArrayList<Equipment> getAvailable() {
        ArrayList<Equipment> borrowed = getBorrowed();
        ArrayList<Equipment> available = getAll();
        for (Equipment e : borrowed) {
            if (available.contains(e)) {
                available.remove(e);
            }
        }
        return available;
    }

    public int countAll() {
        return getAll().size();
    }

    public int countEquipments(Class<? extends Equipment> equipmentClass) {
        ArrayList<Equipment> all = getAll();
        int count = 0;
        for(Equipment e : all) {
            if(equipmentClass == e.getClass()) {
                count++;
            }
        }
        return count;
    }
}
