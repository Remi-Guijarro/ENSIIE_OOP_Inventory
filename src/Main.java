import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import database.SerializeDatabase;
import inventory.*;
import inventory.equipments.Tablet;
import inventory.stocks.Inventory;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import users.Student;
import users.Users;

import java.util.Calendar;

public class Main extends Application{
    public static Stage mainPrimaryStage;
    public static ContextContainer contextContainer;

    private void startContext(){
        Student studentA = new Student("0123456789","firstNameA","surnameA", "adresseA","1234567890","email@email.email",Student.Grade._2A);
        Incubator incubator = new Incubator("C20","DebutHaut","123");
        Startup startup = new Startup("Tarbernak","345",incubator);
        School school = new School("Ensiie");
        Users b = new Users();
        b.addUser(studentA);
        b.addUser(startup);

        Tablet tab =  new Tablet("Oxygen6","Apple",school,Calendar.getInstance().getTime(),399.0,Tablet.OS.LINUX,new int[]{1920,1080});
        Tablet tab2 =  new Tablet("Oxygen7","Apple",school,Calendar.getInstance().getTime(),499.0,Tablet.OS.LINUX,new int[]{1920,1080});

        InventoryManager inventoryManager = InventoryManager.getInstance();
        Inventory inventory = Inventory.getInstance();
        inventoryManager.setInventory(inventory);
        inventoryManager.addEquipment(tab);
        inventoryManager.addEquipment(tab2);

        BorrowingsList b3 = BorrowingsList.getInstance();
        b3.addBorrowedItem(tab,Calendar.getInstance().getTime(),"Why not",studentA);


        contextContainer = new ContextContainer(b);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainPrimaryStage = primaryStage;
        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("gui/main.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Inventory");
        BorderlessScene bordelessScene = new BorderlessScene(primaryStage, StageStyle.UNDECORATED, root, 1000, 800);
        primaryStage.setScene(bordelessScene);
        bordelessScene.setMoveControl(root.lookup("#root"));
        bordelessScene.setSnapEnabled(true);
        bordelessScene.removeDefaultCSS();
        //SerializeDatabase ser = new SerializeDatabase();
        //ser.save(new ContextContainer());
        //startContext();
        primaryStage.show();
    }
}
