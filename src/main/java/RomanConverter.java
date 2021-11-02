import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RomanConverter {
    public static int romanToArabic(String input) {
        List<Roman> romans = new ArrayList<>();
        int value = 0;
        for (int i = 0; i < input.length(); i++)// fills the list
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
    public static String arabicToRoman(int number, Label resultLabel) {
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
    public enum Roman {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);
        public final int value;

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
