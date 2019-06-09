package inventory_app.model.inventory;

import inventory_app.model.inventory.equipements.DepthSensor;
import inventory_app.model.users.Student;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BorrowableTest {

    @Test
    void setBorrowed() {
        Calendar cal = Calendar.getInstance();
        cal.set(2018, Calendar.OCTOBER,22);
        Date bought = cal.getTime();

        inventory_app.model.inventory.Borrowable borrowable = new DepthSensor("Sens8", "INdepth", new inventory_app.model.inventory.School("ENSIIE"),
                bought, 49.99, inventory_app.model.inventory.Equipment.Condition.GOOD);
        inventory_app.model.inventory.Borrower borrower = new Student("1234567891011", "A", "B",
                "ABC", "0909090909", "azerty@abc.com", Student.Grade.JIN);

        boolean result = inventory_app.model.inventory.BorrowingsList.getInstance().isBorrowed(borrowable);
        assertFalse(result);

        borrowable.setBorrowed(Calendar.getInstance().getTime(), "Project", borrower);

        result = inventory_app.model.inventory.BorrowingsList.getInstance().isBorrowed(borrowable);
        assertTrue(result);
    }

    @Test
    void setReturned() {
        Calendar cal = Calendar.getInstance();
        cal.set(2018, Calendar.OCTOBER,22);
        Date bought = cal.getTime();

        inventory_app.model.inventory.Borrowable borrowable = new DepthSensor("Sens8", "INdepth", new inventory_app.model.inventory.School("ENSIIE"),
                bought, 49.99, inventory_app.model.inventory.Equipment.Condition.GOOD);
        inventory_app.model.inventory.Borrower borrower = new Student("1234567891011", "A", "B",
                "ABC", "0909090909", "azerty@abc.com", Student.Grade.JIN);

        borrowable.setBorrowed(Calendar.getInstance().getTime(), "Project", borrower);
        boolean result = inventory_app.model.inventory.BorrowingsList.getInstance().isBorrowed(borrowable);
        assertTrue(result);

        borrowable.setReturned();
        result = inventory_app.model.inventory.BorrowingsList.getInstance().isBorrowed(borrowable);
        assertFalse(result);
    }
}