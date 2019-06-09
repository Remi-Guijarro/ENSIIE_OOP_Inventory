package model.inventory;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Incubator extends Institute implements Serializable {
    private List<Startup> startups;

    public Incubator(String incubatorName, String startupName, String startupSIREN) {
        super(incubatorName);
        Startup s = new Startup(startupName,startupSIREN, this);
        startups = new ArrayList<>();
        startups.add(s);
    }

    public void addStartup(String name, String SIREN) {
        Startup s = new Startup(name, SIREN, this);
        startups.add(s);
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
