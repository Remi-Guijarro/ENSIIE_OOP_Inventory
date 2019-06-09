package inventory_app.model.inventory;

import inventory_app.model.users.Users;

import java.io.Serializable;


public class ContextContainer implements Serializable {
    private inventory_app.model.inventory.BorrowingsList borrowingsList;
    private inventory_app.model.inventory.InventoryManager inventoryManager;
    private Users users;

    public ContextContainer(ContextContainer contextContainer){
        this.borrowingsList = contextContainer.borrowingsList;
        this.users = contextContainer.users;
        this.inventoryManager = contextContainer.inventoryManager;
    }

    public ContextContainer(Users users) {
        this.users = users;
        borrowingsList = inventory_app.model.inventory.BorrowingsList.getInstance();
        inventoryManager = inventory_app.model.inventory.InventoryManager.getInstance();
    }

    public inventory_app.model.inventory.BorrowingsList getBorrowingsList() {
        return borrowingsList;
    }

    public Users getUsers() {
        return users;
    }

    public inventory_app.model.inventory.InventoryManager getInventoryManager(){
        return inventoryManager;
    }
}
