package inventory_app;

import inventory_app.model.database.SerializeDatabase;
import inventory_app.model.inventory.BorrowingsList;
import inventory_app.model.inventory.ContextContainer;
import inventory_app.model.inventory.Incubator;
import inventory_app.model.inventory.InventoryManager;
import inventory_app.model.inventory.School;
import inventory_app.model.inventory.Startup;
import inventory_app.model.inventory.equipements.Tablet;
import inventory_app.model.inventory.stocks.Inventory;
import inventory_app.model.users.Teacher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import inventory_app.model.users.Student;
import inventory_app.model.users.Users;

import java.util.Calendar;

public class Main extends Application{
    public static ContextContainer contextContainer;
    private static SerializeDatabase database;

    private static void startContext(){
        Student studentA = new Student("0123456789","firstNameA","surnameA", "adresseA","1234567890","email@email.email",Student.Grade._2A);
        Student studentB = new Student("0123456788","firstNameB","surnameB", "adresseB","1234557890","email2@email2.email",Student.Grade._1A);
        Teacher teacher = new Teacher("0128484937","TeacherA","Teacher Surname","10 rue de l'exemple","0123654398","prof@mail.fr");
        Incubator incubator = new Incubator("C20","DebutHaut","123");
        Startup startup = new Startup("Tarbernak","345",incubator);
        School school = new School("Ensiie");
        Users b = new Users();
        b.addUser(studentA);
        b.addUser(studentB);
        b.addUser(startup);
        b.addUser(teacher);
        for(int i = 0 ; i < 250 ; i++){
            b.addUser(new Student("01234567"+i,"firstName" +i,"surname" + i, "adresse" + i,"12345578" + i,"email2@email2.email" + i,Student.Grade._1A));
        }

        Tablet tab =  new Tablet("Oxygen6","Apple",school,Calendar.getInstance().getTime(),399.0,Tablet.OS.LINUX,new int[]{1920,1080});
        Tablet tab2 =  new Tablet("Oxygen7","Apple",school,Calendar.getInstance().getTime(),499.0,Tablet.OS.LINUX,new int[]{1920,1080});

        inventory_app.model.inventory.InventoryManager inventoryManager = InventoryManager.getInstance();
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
        Parent root = FXMLLoader.load(this.getClass().getResource("view/main/main.fxml"));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.getIcons().add(new Image(this.getClass().getResource("../icons/inventory.png").toExternalForm()));
        primaryStage.setTitle("Inventory");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args){
        database = new SerializeDatabase();
        startContext();
        //contextContainer =  database.load();
        launch();
        database.save(contextContainer);
    }
}
