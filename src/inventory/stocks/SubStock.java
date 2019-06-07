package inventory.stocks;

import java.io.Serializable;

public class SubStock extends AbstractStock implements Serializable {
    private String emplacement;

    public SubStock(String emplacement) {
        super();
        this.emplacement = emplacement;
    }

}
