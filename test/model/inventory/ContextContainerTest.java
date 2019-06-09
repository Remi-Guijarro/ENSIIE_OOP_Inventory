package model.inventory;

import model.inventory.stocks.Inventory;
import model.database.SerializeDatabase;
import model.inventory.equipments.Tablet;
import model.user.Student;
import model.user.Users;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class ContextContainerTest {

    private model.inventory.ContextContainer contextContainer;

    @Test
    public void createContext(){
        Student studentA = new Student("0123456789","firstNameA","surnameA", "adresseA","1234567890","email@email.email",Student.Grade._2A);
        model.inventory.Incubator incubator = new model.inventory.Incubator("C20","DebutHaut","123");
        model.inventory.Startup startup = new model.inventory.Startup("Tarbernak","345",incubator);
        model.inventory.School school = new model.inventory.School("Ensiie");
        Users b = new Users();
        b.addUser(studentA);
        b.addUser(startup);

        Tablet tab =  new Tablet("Oxygen6","Apple",school,Calendar.getInstance().getTime(),399.0,Tablet.OS.LINUX,new int[]{1920,1080});
        Tablet tab2 =  new Tablet("Oxygen7","Apple",school,Calendar.getInstance().getTime(),499.0,Tablet.OS.LINUX,new int[]{1920,1080});

        model.inventory.InventoryManager inventoryManager = model.inventory.InventoryManager.getInstance();
        Inventory inventory = Inventory.getInstance();
        inventoryManager.setInventory(inventory);
        inventoryManager.addEquipment(tab);
        inventoryManager.addEquipment(tab2);

        model.inventory.BorrowingsList b3 = model.inventory.BorrowingsList.getInstance();
        b3.addBorrowedItem(tab,Calendar.getInstance().getTime(),"Why not",studentA);


        this.contextContainer = new model.inventory.ContextContainer(b);

        assertNotEquals(null,contextContainer);
        assertEquals(2,contextContainer.getUsers().get().size());
        assertEquals(2,contextContainer.getInventoryManager().getAll().size());
        assertEquals(1,contextContainer.getBorrowingsList().getBorrowings().size());
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
            assertEquals(1,contextContainer.getBorrowingsList().getBorrowings().size());
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

}