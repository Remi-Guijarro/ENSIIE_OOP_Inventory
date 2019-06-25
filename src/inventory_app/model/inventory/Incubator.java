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

    public void addStartup(String name, String SIREN) {
        Startup s = new Startup(name, SIREN, this);
        startups.add(s);
    }

    public void addStartup(Startup s) {
        startups.add(s);
    }

    public void removeStartup(Startup s) {
        startups.remove(s);
    }

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

    public List<Startup> getStartups() {
        return startups;
    }
}
