package model.inventory;

import model.inventory.equipments.DepthSensor;
import model.inventory.equipments.Tablet;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentTest {

    @Test
    void createEquipement() {
        School ensiie = new School("ENSIIE");
        Equipment equipment1 = new DepthSensor("Sens8", "INdepth", ensiie,
                Calendar.getInstance().getTime(), 49.99);
        Equipment equipment2 = new DepthSensor("Sens8", "INdepth", ensiie,
                Calendar.getInstance().getTime(), 49.99);
        Equipment equipment3 = new Tablet("Surface Pro 6", "Windows", ensiie,
                Calendar.getInstance().getTime(), 1069.99, Tablet.OS.WINDOWS, new int[] {2736,1824});
        assertEquals("DepthSensor_01", equipment1.getReference());
        assertEquals("DepthSensor_02", equipment2.getReference());
        assertEquals("Tablet_01", equipment3.getReference());
    }

    @Test
    void equals() {
        School ensiie = new School("ENSIIE");
        Equipment equipment1 = new DepthSensor("Sens8", "INdepth", ensiie,
                Calendar.getInstance().getTime(), 49.99);
        assertTrue(equipment1.equals(equipment1));
        assertFalse(equipment1.equals(null));
        assertFalse(equipment1.equals(
                new DepthSensor("Sens8", "INdepth", ensiie,
                        Calendar.getInstance().getTime(), 49.99))
        );
    }
}