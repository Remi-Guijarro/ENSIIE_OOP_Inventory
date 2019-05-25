package inventory;

import inventory.equipments.DepthSensor;
import org.junit.jupiter.api.Test;
import users.Student;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BorrowableTest {

    @Test
    void setBorrowed() {
        Calendar cal = Calendar.getInstance();
        cal.set(2018, Calendar.OCTOBER,22);
        Date bought = cal.getTime();

        Borrowable borrowable = new DepthSensor("Sens8", "INdepth", new School("ENSIIE"),
                bought, 49.99, Equipment.Condition.GOOD);
        Borrower borrower = new Student("1234567891011", "A", "B",
                "ABC", "0909090909", "azerty@abc.com", Student.Grade.JIN);

        boolean result = BorrowingsList.getInstance().isBorrowed(borrowable);
        assertFalse(result);

        borrowable.setBorrowed(Calendar.getInstance().getTime(), "Project", borrower);

        result = BorrowingsList.getInstance().isBorrowed(borrowable);
        assertTrue(result);
    }

    @Test
    void setReturned() {
        Calendar cal = Calendar.getInstance();
        cal.set(2018, Calendar.OCTOBER,22);
        Date bought = cal.getTime();

        Borrowable borrowable = new DepthSensor("Sens8", "INdepth", new School("ENSIIE"),
                bought, 49.99, Equipment.Condition.GOOD);
        Borrower borrower = new Student("1234567891011", "A", "B",
                "ABC", "0909090909", "azerty@abc.com", Student.Grade.JIN);

        borrowable.setBorrowed(Calendar.getInstance().getTime(), "Project", borrower);
        boolean result = BorrowingsList.getInstance().isBorrowed(borrowable);
        assertTrue(result);

        borrowable.setReturned();
        result = BorrowingsList.getInstance().isBorrowed(borrowable);
        assertFalse(result);
    }
}