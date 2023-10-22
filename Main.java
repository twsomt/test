import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Calculator {
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
    public static void main(String[] args) {
        try {
            // Создание объекта Scanner для ввода с клавиатуры
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ввод:");

            // Чтение введенной строки и разделение её на части по пробелу
            String[] parts = scanner.nextLine().split(" ");
            String result = calculate(parts);

            // Вывод результата
            System.out.println("Вывод:");
            System.out.println(result);
        } catch (Exception error) {
            System.out.println("Исключение: " + error.getMessage());
        }
    }

    // Проверяем римская ли цифра
    public static boolean isRomanNumeral(String s) {
        return romanNumerals.containsKey(s);
    }

    // Римская => Арабская
    public static int toArabic(String roman) {
        return romanNumerals.get(roman);
    }

    // Арабская => Римская
    public static String toRoman(int arabic) {
        if (arabic < 1 || arabic > 10) {
            throw new IllegalArgumentException("Результат д.б. в диапазоне от 1 до 10");
        }

        for (Map.Entry<String, Integer> entry : romanNumerals.entrySet()) {
            if (entry.getValue() == arabic) {
                return entry.getKey();
            }
        }
        return "";
    }

    // Калькуляруем
    public static String calculate(String[] parts) {
        if (parts.length != 3) {
            throw new IllegalArgumentException("Получено меньше данных чем ожидалось");
        }

        // Получаем операнды и оператор
        String a = parts[0];
        String op = parts[1];
        String b = parts[2];

        // Парсим операнды, в зависимости от их типа (арабские или римские числа)
        int intA = parseOperand(a);
        int intB = parseOperand(b);

        // Сама операция
        int result = performOperation(intA, op, intB);

        // Возвращаем результат
        return isRomanNumeral(a) ? toRoman(result) : String.valueOf(result);
    }

    // Определяем тип числа и возвращаем его (число)
    private static int parseOperand(String operand) {
        if (isRomanNumeral(operand)) {
            return toArabic(operand);
        } else {
            return Integer.parseInt(operand);
        }
    }

    // Калькуляруем
    private static int performOperation(int a, String operator, int b) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                int result = a - b;
                if (result < 0 && isRomanNumeral(String.valueOf(a))) {
                    throw new IllegalArgumentException("В римской системе нет отрицательных чисел");
                }
                return result;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Недопустимая операция");
        }
    }
}
