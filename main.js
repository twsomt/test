// Массив исходных данных
const romanNumerals = {
    I: 1,
    II: 2,
    III: 3,
    IV: 4,
    V: 5,
    VI: 6,
    VII: 7,
    VIII: 8,
    IX: 9,
    X: 10
};

// Проверяем римская ли цифра
function isRomanNumeral(s) {
    return romanNumerals[s] !== undefined;
}

// Римская => Арабская
function toArabic(roman) {
    return romanNumerals[roman];
}

// Арабская => Римская
function toRoman(arabic) {
    if (arabic < 1 || arabic > 10) {
        throw new Error("Результат должен быть в диапазоне от 1 до 10");
    }

    for (const key in romanNumerals) {
        if (romanNumerals[key] === arabic) {
            return key;
        }
    }

    return "";
}

// Калькуляруем
function calculate(parts) {
    if (parts.length !== 3) {
        throw new Error("Формат входных данных не соответствует ожидаемому");
    }

    // Получаем операнды и оператор
    const a = parts[0];
    const op = parts[1];
    const b = parts[2];

    // Парсим операнды, в зависимости от их типа (арабские или римские числа)
    const intA = parseOperand(a);
    const intB = parseOperand(b);

    // Сама операция
    const result = performOperation(intA, op, intB);

    // Возвращаем результат
    return isRomanNumeral(a) ? toRoman(result) : String(result);
}

// Определяем тип числа и возвращаем его (число)
function parseOperand(operand) {
    if (isRomanNumeral(operand)) {
        return toArabic(operand);
    } else {
        return parseInt(operand, 10);
    }
}

// Калькуляруем
function performOperation(a, operator, b) {
    switch (operator) {
        case "+":
            return a + b;
        case "-":
            const result = a - b;
            if (result < 0 && isRomanNumeral(a.toString())) {
                throw new Error("В римской системе нет отрицательных чисел");
            }
            return result;
        case "*":
            return a * b;
        case "/":
            if (b === 0) {
                throw new Error("Деление на ноль");
            }
            return Math.floor(a / b);
        default:
            throw new Error("Недопустимая операция");
    }
}

// Мэин метод
try {
    const readline = require('readline');
    const rl = readline.createInterface({
        input: process.stdin,
        output: process.stdout
    });

    rl.question("Ввод: ", (input) => {
        const parts = input.split(" ");
        try {
            const result = calculate(parts);
            console.log(result);
        } catch (error) {
            console.log("Исключение: " + error.message);
        }
        rl.close();
    });
} catch (error) {
    console.log("Исключение: " + error.message);
}
