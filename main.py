# Массив исходных данных
roman_numerals = {
    'I': 1,
    'II': 2,
    'III': 3,
    'IV': 4,
    'V': 5,
    'VI': 6,
    'VII': 7,
    'VIII': 8,
    'IX': 9,
    'X': 10
}


def is_roman_numeral(s):
    """Проверяем римская ли цифра."""
    return s in roman_numerals


def to_arabic(roman):
    """Римская => Арабская."""
    return roman_numerals[roman]


def to_roman(arabic):
    """Арабская => Римская."""
    if arabic < 1 or arabic > 10:
        raise ValueError("Результат д.б. в диапазоне от 1 до 10")

    for key, value in roman_numerals.items():
        if value == arabic:
            return key

# Калькуляруем
def calculate(parts):
    if len(parts) != 3:
        raise ValueError("Формат входных данных не соответствует ожидаемому")

    # Получаем операнды и оператор
    a, op, b = parts

    # Парсим операнды, в зависимости от их типа (арабские или римские числа)
    int_a = parse_operand(a)
    int_b = parse_operand(b)

    # Сама операция
    result = perform_operation(int_a, op, int_b)

    # Возвращаем результат
    return to_roman(result) if is_roman_numeral(a) else str(result)


def parse_operand(operand):
    """Определяем тип числа и возвращаем его (число)."""
    if is_roman_numeral(operand):
        return to_arabic(operand)
    else:
        return int(operand)


def perform_operation(a, operator, b):
    """Калькуляруем."""
    if operator == "+":
        return a + b
    elif operator == "-":
        result = a - b
        if result < 0 and is_roman_numeral(str(a)):
            raise ValueError("В римской системе нет отрицательных чисел")
        return result
    elif operator == "*":
        return a * b
    elif operator == "/":
        if b == 0:
            raise ZeroDivisionError("Деление на ноль")
        return a // b
    else:
        raise ValueError("Недопустимая операция")


# Иф мэйн стартУем
if __name__ == "__main__":
    try:
        # Ввод данных от юзера
        user_input = input("Ввод: ")
        parts = user_input.split()
        result = calculate(parts)
        print(result)
    except Exception as error:
        print(f"Исключение: {error}")
