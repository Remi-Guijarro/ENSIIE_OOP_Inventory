package inventory;

import java.io.Serializable;
import java.util.ArrayList;

public class BorrowablesList implements Serializable {
    private ArrayList<Borrowable> borrowables;

    private static BorrowablesList ourInstance = new BorrowablesList();

    public static BorrowablesList getInstance() {
        return ourInstance;
    }

    private BorrowablesList() {
        this.borrowables = new ArrayList<>();
    }

    public ArrayList<Borrowable> getBorrowables(){
        return this.borrowables;
    }
}
