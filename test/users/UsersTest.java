package users;

import inventory.Borrower;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {
    private static Teacher p1 = new Teacher("269054958880", "Nathalie", "Durand",
            "123, rue Victor Hugo, 37000 TOURS", "0701020304", "nath.durant@email.com");
    private static Student p2 = new Student("177023523800522", "Henry", "Smith",
            "456, Place de la Victoire, 91000 EVRY", "0321998877", "smithh@email.com",
            Student.Grade._2A);
    private static Teacher p3 = new Teacher ("123456789011", "John", "Lock",
            "666 Lost Island, 00000 NOWHERE", "0700000000", "noname@none.no");


    @Test
    void addUser() {
        Users users = new Users();
        users.addUser(p1);
        users.addUser(p2);
        ArrayList<Borrower> list = users.get();
        assertEquals(2, list.size());
        assertEquals("269054958880", ((People)list.get(0)).getId());
        assertEquals("smithh@email.com", ((People)list.get(1)).getEmail());
    }

    @Test
    void removeUser() {
        Users users = new Users();
        users.addUser(p1);
        users.addUser(p2);
        ArrayList<Borrower> list = users.get();
        assertEquals(2, list.size());
        users.removeUser(p1);
        list = users.get();
        assertEquals(1, list.size());
    }

    @Test
    void removeUserNotExists() {
        Users users = new Users();
        users.addUser(p1);
        users.addUser(p2);
        try {
            users.removeUser(p3);
        } catch (NoSuchElementException e) {
            return;
        }
        fail();
    }

    @Test
    void exists() {
        Users users = new Users();
        users.addUser(p1);
        users.addUser(p2);
        boolean result = users.exists(p1);
        assertTrue(result);
        result = users.exists(p3);
        assertFalse(result);
    }
}