package inventory_app.model.database;

import inventory_app.model.inventory.ContextContainer;

import java.io.*;

/**
 *  SerializeDatabase :  represent a saving and loading strategy using Serialization
 */
public class SerializeDatabase implements Savable, Loadable{

    @Override
    public ContextContainer load() {
        try {
            FileInputStream file = new FileInputStream("resources/saves/save.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            ContextContainer save = (ContextContainer)in.readObject();
            in.close();
            file.close();
            return save;
        } catch(IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public void save(ContextContainer contextContainer) {
        try {
            FileOutputStream file = new FileOutputStream("resources/saves/save.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(contextContainer);
            out.close();
            file.close();
        } catch(IOException IOE) {
            IOE.printStackTrace();
        }
    }
}
