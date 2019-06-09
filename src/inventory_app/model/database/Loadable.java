package inventory_app.model.database;

import inventory_app.model.inventory.ContextContainer;

public interface Loadable {
    ContextContainer load();
}
