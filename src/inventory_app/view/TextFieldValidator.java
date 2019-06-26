package inventory_app.view;

import javafx.scene.control.TextField;

public class TextFieldValidator {

    public enum FieldREGEX {
        ANY(".*"),
        NAME_REGEX("[a-zA-Z\\S-][a-zéèçA-ZÉÈÇ\\s-]+"),
        EMAIL_REGEX("^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$"),
        PHONE_NUMBER_REGEX("^0([0-9]{9})$"),
        SIREN_REGEX("[0-9]{3}[ \\.\\-]?[0-9]{3}[ \\.\\-]?[0-9]{3}"),
        NUMBER_REGEX("\\d+(\\.\\d{1,2})?"),
        INTEGER_REGEX("\\d+"),
        SOCIAL_SECURITY_NUMBER_FR_REGEX(
                "^" +
                        "([1278])" +
                        "([0-9]{2})" +
                        "(0[1-9]|1[0-2]|20)" +
                        "([02][1-9]|2[AB]|[1345678][0-9]|9[012345789])" +
                        "([0-9]{3})(00[1-9]|0[1-9][0-9]|[1-9][0-9]{2})" +
                        "(0[1-9]|[1-8][0-9]|9[1-7])" +
                "$");

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
