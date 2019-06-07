package inventory;

import inventory.stocks.Inventory;
import users.Users;

import java.io.Serializable;

public class ContextContainer implements Serializable {
    private BorrowingsList borrowingsList;
    private InventoryManager inventoryManager;
    private Users users;

    public ContextContainer(ContextContainer contextContainer){
        this.borrowingsList = contextContainer.borrowingsList;
        this.users = contextContainer.users;
        this.inventoryManager = contextContainer.inventoryManager;
    }

    public ContextContainer(Users users) {
        this.users = users;
        borrowingsList = BorrowingsList.getInstance();
        inventoryManager = InventoryManager.getInstance();
    }

    public BorrowingsList getBorrowingsList() {
        return borrowingsList;
    }

    public Users getUsers() {
        return users;
    }

    public InventoryManager getInventoryManager(){
        return inventoryManager;
    }
}
