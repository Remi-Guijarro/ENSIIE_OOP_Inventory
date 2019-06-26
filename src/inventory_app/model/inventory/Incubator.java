package inventory_app.model.inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Incubator extends inventory_app.model.inventory.Institute implements Serializable {
    private List<inventory_app.model.inventory.Startup> startups;

    public Incubator(String incubatorName) {
        super(incubatorName);
        startups = new ArrayList<>();
    }

    /**
     * @param name
     * @param SIREN
     * create a new Startup to the incubator
     */
    public void addStartup(String name, String SIREN) {
        Startup s = new Startup(name, SIREN, this);
        startups.add(s);
    }

    /**
     * @param s Startup
     * Add a new startup to the incubator
     */
    public void addStartup(Startup s) {
        startups.add(s);
    }

    /**
     * @param s
     * remove the given startup
     */
    public void removeStartup(Startup s) {
        startups.remove(s);
    }

    /**
     * @param SIREN
     * remove a startup by her SIREN
     */
    public void removeStartup(String SIREN) {
        Optional<Startup> s = startups.stream()
                .filter(item -> item.getSIREN().equalsIgnoreCase(SIREN))
                .findFirst();
        if (s.isPresent()) {
            startups.remove(s.get());
        } else {
            throw new NoSuchElementException("Startup does not exist.");
        }
    }

    /**
     * @return List<Startup> the incubated startups
     */
    public List<Startup> getStartups() {
        return startups;
    }
}
