package inventory;

import database.SerializeDatabase;
import inventory.equipments.Tablet;
import inventory.stocks.Inventory;
import org.junit.jupiter.api.Test;
import users.Student;
import users.Users;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class ContextContainerTest {

    private ContextContainer contextContainer;

    @Test
    public void createContext(){
        Student studentA = new Student("0123456789","firstNameA","surnameA", "adresseA","1234567890","email@email.email",Student.Grade._2A);
        Incubator incubator = new Incubator("C20","DebutHaut","123");
        Startup startup = new Startup("Tarbernak","345",incubator);
        School school = new School("Ensiie");
        Users b = new Users();
        b.addUser(studentA);
        b.addUser(startup);

        Tablet tab =  new Tablet("Oxygen6","Apple",school,Calendar.getInstance().getTime(),399.0,Tablet.OS.LINUX,new int[]{1920,1080});
        Tablet tab2 =  new Tablet("Oxygen7","Apple",school,Calendar.getInstance().getTime(),499.0,Tablet.OS.LINUX,new int[]{1920,1080});

        InventoryManager inventoryManager = InventoryManager.getInstance();
        Inventory inventory = Inventory.getInstance();
        inventoryManager.setInventory(inventory);
        inventoryManager.addEquipment(tab);
        inventoryManager.addEquipment(tab2);

        BorrowingsList b3 = BorrowingsList.getInstance();
        b3.addBorrowedItem(tab,Calendar.getInstance().getTime(),"Why not",studentA);


        this.contextContainer = new ContextContainer(b);

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