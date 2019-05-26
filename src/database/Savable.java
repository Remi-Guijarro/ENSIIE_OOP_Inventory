package database;

import inventory.ContextContainer;

public interface Savable {
    void save(ContextContainer contextContainer);
}
