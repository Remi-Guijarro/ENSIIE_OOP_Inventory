package inventory_app.model.database;

import inventory_app.model.inventory.ContextContainer;

/**
 *  Interface Savable
 *  represent a saving algorithm strategy
 * @author Rémi Guijarro Espinosa
 */
public interface Savable {
    /**
     * Define a strategy for saving a context
     * {@link ContextContainer}
     */
    void save(ContextContainer contextContainer);
}
