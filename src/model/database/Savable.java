package model.database;

import model.inventory.ContextContainer;

public interface Savable {
    void save(ContextContainer contextContainer);
}
