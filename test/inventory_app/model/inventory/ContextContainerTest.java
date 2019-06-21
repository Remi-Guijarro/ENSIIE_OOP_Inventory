package inventory_app.model.inventory;

import inventory_app.model.database.SerializeDatabase;
import inventory_app.model.inventory.equipements.Tablet;
import inventory_app.model.inventory.stocks.Inventory;
import inventory_app.model.users.Student;
import inventory_app.model.users.Users;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class ContextContainerTest {

    private inventory_app.model.inventory.ContextContainer contextContainer;

    @Test
    public void createContext(){
        Student studentA = new Student("0123456789","firstNameA","surnameA", "adresseA","1234567890","email@email.email",Student.Grade._2A);
        inventory_app.model.inventory.Incubator incubator = new Incubator("C20","123");
        inventory_app.model.inventory.Startup startup = new inventory_app.model.inventory.Startup("Tarbernak","345",incubator);
        inventory_app.model.inventory.School school = new inventory_app.model.inventory.School("Ensiie");
        Users b = new Users();
        b.addUser(studentA);
        b.addUser(startup);

        Tablet tab =  new Tablet("Oxygen6","Apple",school,Calendar.getInstance().getTime(),399.0,Tablet.OS.LINUX,new int[]{1920,1080});
        Tablet tab2 =  new Tablet("Oxygen7","Apple",school,Calendar.getInstance().getTime(),499.0,Tablet.OS.LINUX,new int[]{1920,1080});

        inventory_app.model.inventory.InventoryManager inventoryManager = inventory_app.model.inventory.InventoryManager.getInstance();
        Inventory inventory = Inventory.getInstance();
        inventoryManager.setInventory(inventory);
        inventoryManager.addEquipment(tab);
        inventoryManager.addEquipment(tab2);

        tab.setBorrowed(Calendar.getInstance().getTime(),"WHY NOT",studentA);
        tab2.setBorrowed(Calendar.getInstance().getTime(),"WE NEED IT",startup);

        this.contextContainer = new inventory_app.model.inventory.ContextContainer(b);

        assertNotEquals(null,contextContainer);
        assertEquals(2,contextContainer.getUsers().get().size());
        assertEquals(true,contextContainer.getBorrowingsList().isBorrowed(tab));
        assertEquals(true,contextContainer.getBorrowingsList().isBorrowed(tab2));
        assertEquals(2,contextContainer.getInventoryManager().getAll().size());
        assertEquals(2,contextContainer.getBorrowingsList().getBorrowings().size());
        saveContext();
    }


    public void saveContext(){
        try{
            SerializeDatabase serializeDatabase =  new SerializeDatabase();
            serializeDatabase.save(this.contextContainer);
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void loadContext(){
        try{
            SerializeDatabase serializeDatabase =  new SerializeDatabase();
            this.contextContainer = serializeDatabase.load();
            assertNotEquals(null,contextContainer);
            assertEquals(2,contextContainer.getUsers().get().size());
            assertEquals(2,contextContainer.getInventoryManager().getAll().size());
            assertEquals(2,contextContainer.getBorrowingsList().getBorrowings().size());
            assertEquals(true,contextContainer.getBorrowingsList().isBorrowed(contextContainer.getInventoryManager().getAll().get(0)));
            assertEquals(true,contextContainer.getBorrowingsList().isBorrowed(contextContainer.getInventoryManager().getAll().get(1)));
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

}