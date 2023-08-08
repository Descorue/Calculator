import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Calculator {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static String sample;
    public static void main(String[] args) throws Exception {
        System.out.println("Введите данные одной строкой в формате: Число действие число. Пробелы обязательны");
        sample = reader.readLine();
        System.out.println(calc(sample));

    }

    public static String calc(String input) throws Exception {
        int a;
        int b;
        int result = 0;
        String sign;

        String[] strings = input.split(" ");
        if (strings.length != 3){
            throw new Exception();
        }

        boolean isRoman = false;
        try { //проверяем на то, чтобы число было целым и если оно не парсится, то пробуем конвертировать в римские
            a = Integer.parseInt(strings[0]);
            b = Integer.parseInt(strings[strings.length - 1]);
        } catch (NumberFormatException e) {
            try {
                a = Converter.toArabic(strings[0]);
                b = Converter.toArabic(strings[strings.length - 1]);
                isRoman = true;
            } catch (Exception exception) {
                throw new Exception();
            }
        }

        if (a > 11 || b > 11 || a <= 0 || b <= 0) { //проверка на то, что бы число было не больше 10 и не меньше либо равно нуля
            throw new Exception();
        }


        sign = strings[1];
        if (!sign.equals("+") && !sign.equals("-") && !sign.equals("*") && !sign.equals("/")) { //проверка знаков
            throw new Exception();
        } else {
            switch (sign) { //свитчим знак и считаем
                case ("+") -> result = a + b;
                case ("-") -> result = a - b;
                case ("*") -> result = a * b;
                case ("/") -> result = a / b;
            }
            if (isRoman) {
                try { //проверка на нулл в конвертере
                    return Converter.toRoman(result);
                } catch (NullPointerException e) {
                    throw new Exception();
                }
            }
        }
        return String.valueOf(result);
    }

    static class Converter {
        private final static TreeMap<Integer, String> map = new TreeMap<>();

        static {
            map.put(100, "C");
            map.put(90, "XC");
            map.put(50, "L");
            map.put(40, "XL");
            map.put(10, "X");
            map.put(9, "IX");
            map.put(5, "V");
            map.put(4, "IV");
            map.put(1, "I");
        }

        private static List<String> roman = new ArrayList<>();

        static {
            roman.add("нулевое значение");
            roman.add("I");
            roman.add("II");
            roman.add("III");
            roman.add("IV");
            roman.add("V");
            roman.add("VI");
            roman.add("VII");
            roman.add("VIII");
            roman.add("IX");
            roman.add("X");
        }

        public static String toRoman(int number) {
            int key = map.floorKey(number);
            if (number == key) {
                return map.get(number);
            }
            return map.get(key) + toRoman(number - key);
        }
        public static int toArabic(String number) {
            return roman.indexOf(number);
        }
    }
}

