package inventory_app.model.users;

import inventory_app.model.inventory.Borrower;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Users : Keeper and manager of the list of Borrowers
 */
public class Users implements Serializable {
    private ArrayList<Borrower> users = new ArrayList<>();

    /**
     * @param user {@link Borrower}
     * Add a new Borrower to the list
     */
    public void addUser(Borrower user) {
        users.add(user);
    }


    /**
     * @param user {@link Borrower}
     * remove the specified borrower from the list
     */
    public void removeUser(Borrower user) throws NoSuchElementException {
        if (!exists(user)) throw new NoSuchElementException("The user does not exist.");

        users.remove(user);
    }

    /**
     * @param user {@link Borrower}
     * @return true if the given borrower exist in the list false otherwise
     */
    public boolean exists(Borrower user) {
        if (users.contains(user))
            return true;
        return false;
    }

    /**
     * @return ArrayList<Borrower> {@link Borrower}
     * return the list of borrowers
     */
    public ArrayList<Borrower> get() {
        return users;
    }
}
