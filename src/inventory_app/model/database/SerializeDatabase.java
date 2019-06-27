package inventory_app.model.database;

import inventory_app.model.inventory.ContextContainer;

import java.io.*;

/**
 *  SerializeDatabase :  represent a saving and loading strategy using Serialization
 * @author Rémi Guijarro Espinosa
 */
public class SerializeDatabase implements Savable, Loadable{

    /**
     * @return {@link ContextContainer} return the saved ContextContainer
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public ContextContainer load() throws IOException, ClassNotFoundException {
        FileInputStream file = null;
        // Two try/catch: quick fix serialization bug when running program from jar
        try {
            file = new FileInputStream(String.valueOf(this.getClass().getClassLoader().getResource("saves/save.ser")));
        } catch (FileNotFoundException e) {
            try {
                file = new FileInputStream("resources/saves/save.ser");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        ObjectInputStream in = new ObjectInputStream(file);
        ContextContainer save = (ContextContainer)in.readObject();
        in.close();
        file.close();
        return save;
    }

    /**
     * @param contextContainer
     * Save the given contextContainer into a file a this location -> resources/saves/save.ser
     */
    @Override
    public void save(ContextContainer contextContainer) {
        try {
            FileOutputStream file = null;
            try {
                file = new FileOutputStream(String.valueOf(this.getClass().getClassLoader().getResource("saves/save.ser")));
            } catch (FileNotFoundException e) {
                try {
                    file = new FileOutputStream("resources/saves/save.ser");
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(contextContainer);
            out.close();
            file.close();
        } catch(IOException IOE) {
            IOE.printStackTrace();
        }
    }
}
