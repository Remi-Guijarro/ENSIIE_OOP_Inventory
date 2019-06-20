package inventory_app.model.database;

import inventory_app.model.inventory.ContextContainer;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *  Interface Loadable
 *  represent a loading algorithm strategy
 */
public interface Loadable {
    /**
     * Define a strategy for loading a saved context
     * {@link ContextContainer}
     * @return ContextContainer
     */
    ContextContainer load() throws IOException, ClassNotFoundException;
}
