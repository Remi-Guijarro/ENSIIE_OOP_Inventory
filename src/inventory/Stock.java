package inventory;

import java.util.Collections;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Stock {
    private ArrayList<Equipment> equipments;

    public Stock() {
        equipments = new ArrayList<>();
    }

    public Stock(ArrayList<Equipment> equipments) { this.equipments = equipments; }

    public ArrayList<Equipment> getEquipments() { return equipments; }

    public void addDevice(Equipment device) {
        equipments.add(device);
    }

    public void addDevices(ArrayList<Equipment> devices) {
        this.equipments.addAll(devices);
    }

    public int countDevices() {
        return equipments.size();
    }

    public int countDevices(Class<? extends Equipment> deviceClass) {
        int count = 0;
        for(Equipment e : equipments) {
            if(deviceClass == e.getClass()) {
                count++;
            }
        }
        return count;
    }

    public Equipment pop(Class<? extends Equipment> deviceClass) throws IllegalArgumentException {
        int index = 0;
        for(Equipment e : equipments) {
            if(deviceClass == e.getClass()) {
                return equipments.remove(index);
            }
            index++;
        }
        throw new IllegalArgumentException("No equipment of this type in stock.");
    }

    public Equipment pop(String reference) {
        int index = 0;
        for(Equipment e : equipments) {
            if(reference.equals(e.getReference())) {
                return equipments.remove(index);
            }
            index++;
        }
        throw new IllegalArgumentException("No equipment with this reference in stock.");
    }

    public Stock getSubStock(Class<? extends Equipment> deviceClass) {
        return new Stock(
                (ArrayList<Equipment>) equipments.stream().filter(deviceClass::isInstance).collect(Collectors.toList())
        );
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Stock{");
        sb.append("equipments=").append(equipments);
        sb.append('}');
        return sb.toString();
    }
}
