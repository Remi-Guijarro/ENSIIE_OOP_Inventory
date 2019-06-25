package inventory_app.view.tabs.inventory.filter.utils;

import inventory_app.view.tabs.inventory.InventoryTableController;
import javafx.scene.control.TableView;

public interface FilterController {
    public void setTableView(TableView<InventoryTableController.EquipmentRow> equipmentTable);
}
