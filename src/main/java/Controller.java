
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TextField firstNumField;

    @FXML
    private Label operationLabel;

    @FXML
    private Label resultLabel;

    @FXML
    private TextField secondNumField;

    boolean typinginFirstField = true;

    public String arabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            if (number == 0) {
                return "";
            } else
                resultLabel.setText(" is not in range (0,4000]");
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }
        List<Roman> romanNumerals = Roman.getReverseSortedValues();
        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            Roman currentSymbol = romanNumerals.get(i);
            if (currentSymbol.value <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.value;
            } else
                i++;

        }

        return sb.toString();
    }

    boolean isValidRomanNum(String num) {
        Pattern pattern = Pattern.compile("(^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(num);
        return matcher.find();
    }

    public int romanToArabic(String input) {
        List<Roman> romans = new ArrayList<>();
        int value = 0;

        for (int i = 0; i < input.length(); i++)
            romans.add(Roman.valueOf(input.charAt(i) + ""));

        while (!romans.isEmpty()) {
            Roman current = romans.remove(0);
            if (!romans.isEmpty() && current.shouldCombine(romans.get(0)))
                value += current.toInt(romans.remove(0));
            else
                value += current.ToInt();
        }

        return value;
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
            resultLabel.setText(arabicToRoman(Integer.parseInt(resultLabel.getText())));
        else {

            resultLabel.setText(romanToArabic(resultLabel.getText()) + "");
        }
        convertField(firstNumField);
        convertField(secondNumField);
    }

    public void convertField(TextField field) {
        if (isArabic(field.getText()))
            field.setText(arabicToRoman(Integer.parseInt(field.getText())));
        else {
            if (isValidRomanNum(field.getText()))
                field.setText(romanToArabic(field.getText()) + "");
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

        if(!isValidRomanNum(firstNumField.getText())){
            firstNumField.setText("Not a correct roman numeral!");
        }
        if(!isValidRomanNum(secondNumField.getText())){
            secondNumField.setText("Not a correct roman numeral!");
        }
        int a = romanToArabic(firstNumField.getText());
        int b = romanToArabic(secondNumField.getText());
        String result;
        if (operationLabel.getText().equals("+"))
            result = arabicToRoman(a + b);
        else if (a > b) result = arabicToRoman(a - b);
        else result = "a should be > b";

        resultLabel.setText(result);
    }

    public void clear(ActionEvent actionEvent) {
        typinginFirstField = true;
        firstNumField.setText("");
        secondNumField.setText("");
        resultLabel.setText("");
    }

    public void test(MouseEvent mouseEvent) {
        resultLabel.setText("");
    }


    public enum Roman {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);
        private final int value;

        private Roman(int value) {
            this.value = value;
        }


        public boolean shouldCombine(Roman next) {
            return this.value < next.value;
        }

        public int toInt(Roman next) {
            return next.value - this.value;
        }

        public int ToInt() {
            return value;
        }

        public static List<Roman> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((Roman e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }
}
