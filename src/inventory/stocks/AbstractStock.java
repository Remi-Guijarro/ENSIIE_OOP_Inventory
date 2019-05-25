package inventory.stocks;

import inventory.Equipment;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public abstract class AbstractStock {
    private ArrayList<Equipment> equipments;

    public AbstractStock() {
        equipments = new ArrayList<>();
    }

    public ArrayList<Equipment> getEquipments() { return equipments; }

    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
    }

    public void addEquipments(ArrayList<Equipment> equipments) {
        this.equipments.addAll(equipments);
    }

    public int countEquipments() {
        return equipments.size();
    }

    public int countEquipments(Class<? extends Equipment> equipmentClass) {
        int count = 0;
        for(Equipment e : equipments) {
            if(equipmentClass == e.getClass()) {
                count++;
            }
        }
        return count;
    }

    public Equipment pop(Class<? extends Equipment> equipmentClass) throws NoSuchElementException {
        int index = 0;
        for(Equipment e : equipments) {
            if(equipmentClass == e.getClass()) {
                return equipments.remove(index);
            }
            index++;
        }
        throw new NoSuchElementException("No equipment of this type in stock.");
    }

    public Equipment pop(String reference) throws NoSuchElementException {
        int index = 0;
        for(Equipment e : equipments) {
            if(reference.equals(e.getReference())) {
                return equipments.remove(index);
            }
            index++;
        }
        throw new NoSuchElementException("No equipment with this reference in stock.");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AbstractStock{");
        sb.append("equipments=").append(equipments);
        sb.append('}');
        return sb.toString();
    }
}
