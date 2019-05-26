package inventory;

import inventory.equipments.Tablet;
import org.junit.jupiter.api.Test;
import users.Student;

import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class ContextContainerTest {

    @Test
    public void createContext(){
        Student studentA = new Student("0123456789","firstNameA","surnameA", "adresseA","1234567890","email@email.email",Student.Grade._2A);
        Incubator incubator = new Incubator("C20","DebutHaut","123");
        Startup startup = new Startup("Tarbernak","345",incubator);
        School school = new School("Ensiie");
        BorrowersList b = BorrowersList.getInstance();
        b.addBorrower(studentA);
        b.addBorrower(startup);

        Tablet tab =  new Tablet("Oxygen6","Apple",school,Calendar.getInstance().getTime(),399.0,Tablet.OS.LINUX,new int[]{1920,1080});
        Tablet tab2 =  new Tablet("Oxygen7","Apple",school,Calendar.getInstance().getTime(),499.0,Tablet.OS.LINUX,new int[]{1920,1080});

        BorrowablesList b2 = BorrowablesList.getInstance();
        b2.addBorrowable(tab);
        b2.addBorrowable(tab2);

        BorrowingsList b3 = BorrowingsList.getInstance();
        b3.addBorrowedItem(tab,Calendar.getInstance().getTime(),"Why not",studentA);


        ContextContainer contextContainer = new ContextContainer();

        assertNotEquals(null,contextContainer);
        assertEquals(2,contextContainer.getBorrowersList().gerBorrowers().size());
        assertEquals(2,contextContainer.getBorrowablesList().getBorrowables().size());
        assertEquals(1,contextContainer.getBorrowingsList().getBorrowings().size());
    }

}