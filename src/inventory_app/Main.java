package inventory_app;

import inventory_app.model.database.SerializeDatabase;
import inventory_app.model.inventory.*;
import inventory_app.model.inventory.equipements.DepthSensor;
import inventory_app.model.inventory.equipements.Smartphone;
import inventory_app.model.inventory.equipements.Tablet;
import inventory_app.model.inventory.equipements.VRHeadset;
import inventory_app.model.inventory.stocks.Inventory;
import inventory_app.model.users.Student;
import inventory_app.model.users.Teacher;
import inventory_app.model.users.Users;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application{
    public static ContextContainer contextContainer;
    private static SerializeDatabase database;
    private static Logger logger;
    private Stage window;

    private static void startContext(){
        Student studentA = new Student("0123456789","Henry ","Bol", "13 rue de l'exemple","0123564387","email@email.fr",Student.Grade._2A);
        Student studentB = new Student("0123456788","Alexandre","Le Grand", "56 rue de la guerre","02345763456","Alexandre.leGrand@gmail.fr",Student.Grade._1A);
        Teacher teacher = new Teacher("0128484937","Emma","Clavier","10 rue de l'exemple","08346745329","emma.cla@gmail.fr");
        Incubator incubator1 = new Incubator("C20");
        Incubator incubator2 = new Incubator("THE BEST INCUBATOR");
        Startup startup1 = new Startup("Natural Solutions","345",incubator1);
        Startup startup2 = new Startup("ArianeWorks","97986876",incubator1);
        Startup startup3 = new Startup("Viser La Lune","897987987",incubator2);
        School school = new School("Ensiie");
        School schoolR = new School("Ecole Random");
        Users b = new Users();
        b.addUser(studentA);
        b.addUser(studentB);
        b.addUser(startup1);
        b.addUser(startup2);
        b.addUser(startup3);
        b.addUser(teacher);

        Tablet tab =  new Tablet("Oxygen6","Apple",school,Calendar.getInstance().getTime(),399.0,Tablet.OS.LINUX,new int[]{1920,1080});
        tab.setLocation(Equipment.Location.ROOM_199);
        Tablet tab2 =  new Tablet("Oxygen7","Apple",schoolR,Calendar.getInstance().getTime(),499.0,Tablet.OS.LINUX,new int[]{1920,1080});
        tab2.setLocation(Equipment.Location.ROOM_266);

        Smartphone smartphone1 = new Smartphone("Iphone X","Apple",school,Calendar.getInstance().getTime(),1500,Equipment.Condition.GOOD,Smartphone.PHONE_OS.IOS,8);
        smartphone1.setLocation(Equipment.Location.ROOM_266);
        Smartphone smartphone2 = new Smartphone("Iphone XV","Apple",school,Calendar.getInstance().getTime(),15000,Equipment.Condition.GOOD,Smartphone.PHONE_OS.IOS,20);
        smartphone2.setLocation(Equipment.Location.ROOM_266);

        DepthSensor sensor = new DepthSensor("RealSense", "Intel", schoolR, Calendar.getInstance().getTime(), 224.67);
        sensor.setLocation(Equipment.Location.ROOM_301);
        VRHeadset vrHeadset = new VRHeadset("HTC Vive","HTC",schoolR,Calendar.getInstance().getTime(),670,Equipment.Condition.GOOD);
        vrHeadset.setLocation(Equipment.Location.ROOM_301);
        inventory_app.model.inventory.InventoryManager inventoryManager = InventoryManager.getInstance();
        Inventory inventory = Inventory.getInstance();
        for(int i = 0 ; i < 5 ; i++){
            Equipment.Condition condition = Equipment.Condition.GOOD;
            if(i % 5 == 0){
                condition = Equipment.Condition.USED;
            }
            Tablet tablet = new Tablet("Oxygen" + i,"Apple",school,Calendar.getInstance().getTime(),499.0 * i + 50,condition,new int[]{1920,1080},Tablet.OS.LINUX);
            tablet.setLocation(Equipment.Location.ROOM_266);
            inventory.addEquipment(tablet);
            Smartphone smartphone = new Smartphone("Iphone X" + i,"Apple",schoolR,Calendar.getInstance().getTime(),1500 * i +100,condition,Smartphone.PHONE_OS.IOS,4 + i);
            smartphone.setLocation(Equipment.Location.ROOM_199);
            inventory.addEquipment(smartphone);
            DepthSensor depthSensor =  new DepthSensor("RealSense" + i, "Intel", school, Calendar.getInstance().getTime(), 224.67 + i,condition);
            depthSensor.setLocation(Equipment.Location.ROOM_199);
            inventory.addEquipment(depthSensor);
            VRHeadset vrHeadset1 =  new VRHeadset("HTC Vive" + i,"HTC",school,Calendar.getInstance().getTime(),670 + 1* 2,condition);
            vrHeadset1.setLocation(Equipment.Location.ROOM_266);
            inventory.addEquipment(vrHeadset1);
        }
        Smartphone smartphone= new Smartphone("Iphone X","Apple",school,Calendar.getInstance().getTime(),1500,Equipment.Condition.GOOD, inventory_app.model.inventory.equipements.Smartphone.PHONE_OS.IOS,4);
        smartphone.setLocation(Equipment.Location.ROOM_301);
        inventory.addEquipment(smartphone);
        Tablet tablet = new Tablet("A2","Asus",school,Calendar.getInstance().getTime(),499.0 ,Equipment.Condition.BROKEN,new int[]{1920,1080},Tablet.OS.WINDOWS);
        tab.setLocation(Equipment.Location.ROOM_199);
        inventory.addEquipment(tablet);
        Tablet tablet1 = new Tablet("A3","Asus",school,Calendar.getInstance().getTime(),599.0 ,Equipment.Condition.GOOD,new int[]{3840,2160 },Tablet.OS.WINDOWS);
        tablet1.setLocation(Equipment.Location.ROOM_266);
        inventory.addEquipment(tablet1);
        inventoryManager.setInventory(inventory);
        inventoryManager.addEquipment(tab);
        inventoryManager.addEquipment(tab2);
        inventoryManager.addEquipment(smartphone1);
        inventoryManager.addEquipment(smartphone2);
        inventoryManager.addEquipment(sensor);
        inventoryManager.addEquipment(vrHeadset);

        BorrowingsList b3 = BorrowingsList.getInstance();
        try {
            b3.addBorrowedItem(tab, new SimpleDateFormat("yyyy-MM-dd").parse("2019-06-02"),"Personnal project",studentA);
            b3.addBorrowedItem(smartphone, new SimpleDateFormat("yyyy-MM-dd").parse("2019-08-02"),"Demo",startup1);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        contextContainer = new ContextContainer(b);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Parent root = FXMLLoader.load(this.getClass().getResource("view/main/main.fxml"));
        window.initStyle(StageStyle.DECORATED);
        window.getIcons().add(new Image(this.getClass().getClassLoader().getResource("icons/inventory.png").toExternalForm()));
        window.setTitle("Inventory");
        Scene scene = new Scene(root);
        setWindowMinSize();
        window.setScene(scene);
        window.setMaximized(true);
        window.show();
    }

    private void setWindowMinSize() {
        window.setMinWidth(500);
        window.setMinHeight(400);
    }

    public static void main(String[] args){
        database = new SerializeDatabase();
        logger = Logger.getLogger(String.valueOf(Main.class));
        try{
            contextContainer =  database.load();
            //contextContainer.computeStaticFields();
        }catch (IOException | ClassNotFoundException e){
            startContext();
            logger.log(Level.SEVERE,"Save not found.An error append during saves loading process. A template context is being imported");
        }finally {
            launch();
        }
        database.save(contextContainer);
    }
}
