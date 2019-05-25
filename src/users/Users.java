package users;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Users {
    private ArrayList<People> users = new ArrayList<>();

    public void addUser(People user) {
        users.add(user);
    }

    public void removeUser(People user) throws NoSuchElementException {
        if (!exists(user)) throw new NoSuchElementException("The user does not exist.");

        users.remove(user);
    }

    public boolean exists(People user) {
        if (users.contains(user))
            return true;
        return false;
    }

    public ArrayList<People> get() {
        return users;
    }
}
