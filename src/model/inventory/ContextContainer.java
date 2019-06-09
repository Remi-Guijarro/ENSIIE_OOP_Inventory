package model.inventory;

import model.user.Users;

import java.io.Serializable;


public class ContextContainer implements Serializable {
    private model.inventory.BorrowingsList borrowingsList;
    private model.inventory.InventoryManager inventoryManager;
    private Users users;

    public ContextContainer(ContextContainer contextContainer){
        this.borrowingsList = contextContainer.borrowingsList;
        this.users = contextContainer.users;
        this.inventoryManager = contextContainer.inventoryManager;
    }

    public ContextContainer(Users users) {
        this.users = users;
        borrowingsList = model.inventory.BorrowingsList.getInstance();
        inventoryManager = model.inventory.InventoryManager.getInstance();
    }

    public model.inventory.BorrowingsList getBorrowingsList() {
        return borrowingsList;
    }

    public Users getUsers() {
        return users;
    }

    public model.inventory.InventoryManager getInventoryManager(){
        return inventoryManager;
    }
}
