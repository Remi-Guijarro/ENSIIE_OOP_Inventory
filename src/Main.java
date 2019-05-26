import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import database.SerializeDatabase;
import inventory.ContextContainer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
    public static Stage mainPrimaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainPrimaryStage = primaryStage;
        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("gui/main.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Inventory");
        BorderlessScene bordelessScene = new BorderlessScene(primaryStage, StageStyle.UNDECORATED, root, 1000, 800);
        primaryStage.setScene(bordelessScene);
        bordelessScene.setMoveControl(root.lookup("#header"));
        bordelessScene.setSnapEnabled(true);
        bordelessScene.removeDefaultCSS();
        SerializeDatabase ser = new SerializeDatabase();
        ser.save(new ContextContainer());
        primaryStage.show();
    }
}
