package model.inventory;

import model.inventory.equipments.DepthSensor;
import model.user.Student;
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

        model.inventory.Borrowable borrowable = new DepthSensor("Sens8", "INdepth", new model.inventory.School("ENSIIE"),
                bought, 49.99, model.inventory.Equipment.Condition.GOOD);
        model.inventory.Borrower borrower = new Student("1234567891011", "A", "B",
                "ABC", "0909090909", "azerty@abc.com", Student.Grade.JIN);

        boolean result = model.inventory.BorrowingsList.getInstance().isBorrowed(borrowable);
        assertFalse(result);

        borrowable.setBorrowed(Calendar.getInstance().getTime(), "Project", borrower);

        result = model.inventory.BorrowingsList.getInstance().isBorrowed(borrowable);
        assertTrue(result);
    }

    @Test
    void setReturned() {
        Calendar cal = Calendar.getInstance();
        cal.set(2018, Calendar.OCTOBER,22);
        Date bought = cal.getTime();

        model.inventory.Borrowable borrowable = new DepthSensor("Sens8", "INdepth", new model.inventory.School("ENSIIE"),
                bought, 49.99, model.inventory.Equipment.Condition.GOOD);
        model.inventory.Borrower borrower = new Student("1234567891011", "A", "B",
                "ABC", "0909090909", "azerty@abc.com", Student.Grade.JIN);

        borrowable.setBorrowed(Calendar.getInstance().getTime(), "Project", borrower);
        boolean result = model.inventory.BorrowingsList.getInstance().isBorrowed(borrowable);
        assertTrue(result);

        borrowable.setReturned();
        result = model.inventory.BorrowingsList.getInstance().isBorrowed(borrowable);
        assertFalse(result);
    }
}