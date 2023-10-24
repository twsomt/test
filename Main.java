import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    // Массив исходных данных
    private static final Map<String, Integer> romanNumerals = new HashMap<>() {{
        put("I", 1);
        put("II", 2);
        put("III", 3);
        put("IV", 4);
        put("V", 5);
        put("VI", 6);
        put("VII", 7);
        put("VIII", 8);
        put("IX", 9);
        put("X", 10);
    }};
    

    // Мэин метод
    // Получаем ввод с клавиатуры и запускаем проверки и калькуляцию
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ввод:");

            String[] parts = scanner.nextLine().split(" ");
            String result = calculate(parts);

            System.out.println("Вывод:");
            System.out.println(result);
        } catch (Exception error) {
            System.out.println("Исключение: " + error.getMessage());
        }
    }

    // Проверяем римская ли цифра
    public static boolean isRomanNumeral(Object input) {
        if (input instanceof String) {
            String s = (String) input;
            return s.matches("^[IVXLCDM]+$");
        } else if (input instanceof Integer) {
            int intValue = (Integer) input;
            return intValue >= 1 && intValue <= 10;
        }
        return false;
    }

    // Римская => Арабская
    public static int toArabic(String roman) {
        Integer arabic = romanNumerals.get(roman);
        if (arabic != null) {
            return arabic;
        } else {
            throw new IllegalArgumentException("Неверное римское число: " + roman);
        }
    }
    
    // Арабская => Римская
    public static String toRoman(int arabic) {
    
        StringBuilder roman = new StringBuilder();
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] numerals = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    
        for (int i = 0; i < values.length; i++) {
            while (arabic >= values[i]) {
                roman.append(numerals[i]);
                arabic -= values[i];
            }
        }
        return roman.toString();
    }

    // Готовимся калькулировать
    public static String calculate(String[] parts) {
        if (parts.length != 3) {
            throw new IllegalArgumentException("Формат входных данных не соответствует ожидаемому");
        }
    
        // Получаем операнды и оператор
        String a = parts[0];
        String op = parts[1];
        String b = parts[2];
    
        // Парсим операнды, в зависимости от их типа (арабские или римские числа)
        int intA = parseOperand(a);
        int intB = parseOperand(b);
    
        // Проверяем, что операнды имеют одинаковый тип (оба римские или оба арабские)
        if ((isRomanNumeral(a) && !isRomanNumeral(b)) || (!isRomanNumeral(a) && isRomanNumeral(b))) {
            throw new IllegalArgumentException("Операнды разных типов (арабские и римские) нельзя обрабатывать одновременно");
        }
    
        // Сама операция
        int result = performOperation(intA, op, intB);
    
        // Возвращаем результат
        return isRomanNumeral(a) ? toRoman(result) : String.valueOf(result);
    }
    


    // Определяем тип числа и возвращаем его (число)
    private static int parseOperand(String operand) {
        int intValue;
        try {
            intValue = Integer.parseInt(operand);
        } catch (NumberFormatException e) {
            // Если не удалось преобразовать в целое число, то проверяем, не римское ли оно часом
            if (isRomanNumeral(operand)) {
                intValue = toArabic(operand);
            } else {
                throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 10 включительно");
            }
        }
    
        if (intValue < 1 || intValue > 10) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 10 включительно");
        }
    
        return intValue;
    }
    
    

    // Калькуляруем
    private static int performOperation(int a, String operator, int b) {
        int result;
        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                if (isRomanNumeral(a) && isRomanNumeral(b) && result < 1) {
                    throw new IllegalArgumentException("В римской системе нет отрицательных чисел");
                }
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                result = a / b;
                break;
            default:
                throw new IllegalArgumentException("Недопустимая операция");
        }
    
        return result;
    }
    
}
