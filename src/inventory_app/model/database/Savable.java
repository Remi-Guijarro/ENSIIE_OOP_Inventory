package inventory_app.model.database;

import inventory_app.model.inventory.ContextContainer;

public interface Savable {
    void save(ContextContainer contextContainer);
}
