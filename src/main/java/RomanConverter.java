import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class RomanConverter {
    public static int romanToArabic(String input) {
        List<Controller.Roman> romans = new ArrayList<>();
        int value = 0;

        for (int i = 0; i < input.length(); i++)
            romans.add(Controller.Roman.valueOf(input.charAt(i) + ""));

        while (!romans.isEmpty()) {
            Controller.Roman current = romans.remove(0);
            if (!romans.isEmpty() && current.shouldCombine(romans.get(0)))
                value += current.toInt(romans.remove(0));
            else
                value += current.ToInt();
        }

        return value;
    }
    public static String arabicToRoman(int number, Label resultLabel) {
        if ((number <= 0) || (number > 4000)) {
            if (number == 0) {
                return "";
            } else
                resultLabel.setText(" is not in range (0,4000]");
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }
        List<Controller.Roman> romanNumerals = Controller.Roman.getReverseSortedValues();
        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            Controller.Roman currentSymbol = romanNumerals.get(i);
            if (currentSymbol.value <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.value;
            } else
                i++;
        }
        return sb.toString();
    }
}
