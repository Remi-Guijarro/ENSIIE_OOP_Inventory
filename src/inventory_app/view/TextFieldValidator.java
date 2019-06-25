package inventory_app.view;

import javafx.scene.control.TextField;

public class TextFieldValidator {

    public enum FieldREGEX {
        ANY(".*"),
        NAME_REGEX("[a-zA-Z\\S-][a-zéèçA-ZÉÈÇ\\s-]+"),
        EMAIL_REGEX("^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$"),
        PHONE_NUMBER_REGEX("^0([0-9]{9})$"),
        SIREN("[0-9]{3}[ \\.\\-]?[0-9]{3}[ \\.\\-]?[0-9]{3}");

        private String REGEX;

        FieldREGEX(String fieldREGEX) {
            this.REGEX = fieldREGEX;
        }

        public String getFieldREGEX() {
            return REGEX;
        }
    }

    public static boolean validate(TextField field, final FieldREGEX regex) {
        if (field.getText().matches(regex.getFieldREGEX())) {
            field.setStyle("-fx-background-color: #a5ff93");
            return true;
        } else {
            field.setStyle("-fx-background-color: #ff998c");
            return false;
        }
    }

    public static void setBlankBackground(TextField field) {
        field.setStyle("-fx-background-color: WHITE");
    }
}
