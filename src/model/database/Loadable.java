package model.database;

import model.inventory.ContextContainer;

public interface Loadable {
    ContextContainer load();
}
