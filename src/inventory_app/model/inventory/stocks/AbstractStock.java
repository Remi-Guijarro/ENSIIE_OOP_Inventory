package inventory_app.model.inventory.stocks;

import inventory_app.model.inventory.Equipment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Describe a Stock for all type of equipments
 */
public abstract class AbstractStock implements Serializable {
    private ArrayList<Equipment> equipments;

    public AbstractStock() {
        equipments = new ArrayList<>();
    }

    /**
     * @return ArrayList<Equipment> the list of equipments
     */
    public ArrayList<Equipment> getEquipments() { return equipments; }

    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
    }

    public void addEquipments(ArrayList<Equipment> equipments) {
        this.equipments.addAll(equipments);
    }


    /**
     * @return the number of equipments in the list
     */
    public int countEquipmentsOfType() {
        return equipments.size();
    }

    /**
     * @param equipmentClass the desired type
     * @return the number of element of the desired type in the stock
     */
    public long countEquipmentsOfType(Class<? extends Equipment> equipmentClass) {
        return this.equipments.stream().filter(equipment -> equipmentClass.isInstance(equipment)).count();
    }

    /**
     * remove the first element of the desired type and return it
     * @param equipmentClass
     * @return return the first element of desired type
     * @throws NoSuchElementException
     */
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

    /**
     * remove the first element with the desired reference and return it
     * @param reference
     * @return return the first element with the given reference
     * @throws NoSuchElementException
     */
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
