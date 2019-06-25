package inventory_app.model;

import inventory_app.model.inventory.Incubator;
import inventory_app.model.inventory.Startup;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class IncubatorTest {
    private Incubator incubator = new Incubator("C19");

    @Test
    void addStartup() {
        incubator.addStartup("Startup1", "SIREN1");
        incubator.addStartup("Startup2", "SIREN2");
        incubator.addStartup("Startup3", "SIREN3");
        incubator.addStartup("Startup4", "SIREN4");
        assertEquals(5, incubator.getStartups().size());
    }

    @Test
    void removeStartup() {
        incubator.addStartup("iWanttodie", "SIRENDELETE");
        incubator.removeStartup("SIRENDELETE");
        for (Startup s : incubator.getStartups()) {
            if (s.getSIREN().equals("SIRENDELETE")) {
                fail();
            }
        }
    }

    @Test
    void removeStartupFail() {
        try {
            incubator.removeStartup("NOTEXIST");
        } catch (NoSuchElementException e) {
            return;
        }
        fail();

    }
}