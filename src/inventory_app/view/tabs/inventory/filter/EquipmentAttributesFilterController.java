package inventory_app.view.tabs.inventory.filter;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class EquipmentAttributesFilterController {

    @FXML
    private GridPane gridPane;

    public void addTextFieldFilter(Label name, TextField field) {
        int rowCount = gridPaneRowCount();
        name.setTextFill(Color.WHITE);
        gridPane.add(name, 0, rowCount);
        gridPane.add(field, 1, rowCount);
    }

    private int gridPaneRowCount() {
        int numRows = gridPane.getRowConstraints().size();
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            Node child = gridPane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null){
                    numRows = Math.max(numRows,rowIndex+1);
                }
            }
        }
        return numRows;
    }
}
