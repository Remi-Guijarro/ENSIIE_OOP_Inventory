package users;

import inventory.Borrower;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Users implements Serializable {
    private ArrayList<Borrower> users = new ArrayList<>();

    public void addUser(Borrower user) {
        users.add(user);
    }

    public void removeUser(Borrower user) throws NoSuchElementException {
        if (!exists(user)) throw new NoSuchElementException("The user does not exist.");

        users.remove(user);
    }

    public boolean exists(Borrower user) {
        if (users.contains(user))
            return true;
        return false;
    }

    public ArrayList<Borrower> get() {
        return users;
    }
}
