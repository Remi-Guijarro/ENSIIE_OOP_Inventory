package inventory;

import java.io.Serializable;
import java.util.ArrayList;

public class BorrowersList implements Serializable {
    private ArrayList<Borrower> borrowers;

    private static BorrowersList ourInstance = new BorrowersList();

    public static BorrowersList getInstance() {
        return ourInstance;
    }

    private BorrowersList() {
        this.borrowers = new ArrayList<>();
    }

    public ArrayList<Borrower> gerBorrowers(){
        return this.borrowers;
    }

    public void addBorrower(Borrower b){
        this.borrowers.add(b);
    }
}
