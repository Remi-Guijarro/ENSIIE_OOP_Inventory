package database;

import inventory.ContextContainer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializeDatabase implements Savable, Loadable{

    @Override
    public void load(ContextContainer contextContainer) {

    }

    @Override
    public void save(ContextContainer contextContainer) {
        try
        {
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
