
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Controller {
    @FXML
    AnchorPane anchorPane;

    @FXML
    private TextField firstNumField, secondNumField;

    @FXML
    private Label operationLabel, resultLabel;

    boolean typinginFirstField = true;


    boolean isValidRomanNum(String num) {
        Pattern pattern = Pattern.compile("(^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(num);
        return matcher.find();
    }


    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    boolean isArabic(String str) {
        try {
            int a = Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void convert(ActionEvent actionEvent) {
        if (isArabic(resultLabel.getText()))
            resultLabel.setText(RomanConverter.arabicToRoman(Integer.parseInt(resultLabel.getText()), resultLabel));
        else {
            resultLabel.setText(RomanConverter.romanToArabic(resultLabel.getText()) + "");
        }
        convertField(firstNumField);
        convertField(secondNumField);
    }

    public void convertField(TextField field) {
        if (isArabic(field.getText()))
            field.setText(RomanConverter.arabicToRoman(Integer.parseInt(field.getText()), resultLabel));
        else {
            if (isValidRomanNum(field.getText()))
                field.setText(RomanConverter.romanToArabic(field.getText()) + "");
            else
                field.setText("Not a correct roman numeral!");
        }
    }

    public void buttonDialed(ActionEvent event) {
        Object node = event.getSource();
        Button btn = (Button) node;
        if (typinginFirstField) {
            if (firstNumField.getText().length() < 8)
                firstNumField.setText(firstNumField.getText() + btn.getText());
        } else {
            if (secondNumField.getText().length() < 8)
                secondNumField.setText(secondNumField.getText() + btn.getText());
        }
    }

    public void plusClicked(ActionEvent actionEvent) {
        typinginFirstField = false;
        operationLabel.setText("+");
    }

    public void minusClicked(ActionEvent actionEvent) {
        typinginFirstField = false;
        operationLabel.setText("-");
    }

    @FXML
    public void initialize() throws IOException {

    }

    public void calculate(ActionEvent actionEvent) {
        if(isArabic(firstNumField.getText())||isArabic(firstNumField.getText())||isArabic(firstNumField.getText())){
            convert(new ActionEvent());
        }
        if (!isValidRomanNum(firstNumField.getText())) {
            firstNumField.setText("Not a correct roman numeral!");
        }
        if (!isValidRomanNum(secondNumField.getText())) {
            secondNumField.setText("Not a correct roman numeral!");
        }
        int a = RomanConverter.romanToArabic(firstNumField.getText());
        int b = RomanConverter.romanToArabic(secondNumField.getText());
        String result;
        if (operationLabel.getText().equals("+"))
            result = RomanConverter.arabicToRoman(a + b, resultLabel);
        else if (a > b) result = RomanConverter.arabicToRoman(a - b, resultLabel);
        else result = "a should be > b";

        resultLabel.setText(result);
    }

    public void clear(ActionEvent actionEvent) {
        typinginFirstField = true;
        firstNumField.setText("");
        secondNumField.setText("");
        resultLabel.setText("");
    }

    public void test(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            calculate(new ActionEvent());
        if (keyEvent.getCode().equals(KeyCode.TAB)) {
            typinginFirstField = !typinginFirstField;
        }
    }



}
