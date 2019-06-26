package inventory_app.model.inventory;

import inventory_app.model.users.Users;

import java.io.Serializable;


public class ContextContainer implements Serializable {
    private inventory_app.model.inventory.BorrowingsList borrowingsList;
    private inventory_app.model.inventory.InventoryManager inventoryManager;
    private Users users;
    //Save the static variables to reassign on load
    //[0] : DepthSensor
    //[1] : Smartphone
    //[2] : Tablet
    //[3] : VRHeadset
    //private ArrayList<Integer> lastIds;

    /**
     * @param contextContainer represent the context of the app, it is a container that contains all the wanted informations for a save
     */
    public ContextContainer(ContextContainer contextContainer){
        this.borrowingsList = contextContainer.borrowingsList;
        this.users = contextContainer.users;
        this.inventoryManager = contextContainer.inventoryManager;

        /*lastIds.add(Equipment.getLastId().get(DepthSensor.class.getSimpleName()));
        lastIds.add(Equipment.getLastId().get(Smartphone.class.getSimpleName()));
        lastIds.add(Equipment.getLastId().get(Tablet.class.getSimpleName()));
        lastIds.add(Equipment.getLastId().get(VRHeadset.class.getSimpleName()));*/
    }

    public ContextContainer(Users users) {
        this.users = users;
        borrowingsList = inventory_app.model.inventory.BorrowingsList.getInstance();
        inventoryManager = inventory_app.model.inventory.InventoryManager.getInstance();

        /*lastIds.add(Equipment.getLastId().get(DepthSensor.class.getSimpleName()));
        lastIds.add(Equipment.getLastId().get(Smartphone.class.getSimpleName()));
        lastIds.add(Equipment.getLastId().get(Tablet.class.getSimpleName()));
        lastIds.add(Equipment.getLastId().get(VRHeadset.class.getSimpleName()));*/
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

    /*public void computeStaticFields() {
        HashMap<String, Integer> lastIdsMap = new HashMap<>();
        lastIdsMap.put(DepthSensor.class.getSimpleName(), lastIds.get(0));
        lastIdsMap.put(Smartphone.class.getSimpleName(), lastIds.get(1));
        lastIdsMap.put(Tablet.class.getSimpleName(), lastIds.get(2));
        lastIdsMap.put(VRHeadset.class.getSimpleName(), lastIds.get(3));
        Equipment.setLastId(lastIdsMap);
    }*/
}
