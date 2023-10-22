-- Массив исходных данных
romanNumerals = {
    I = 1,
    II = 2,
    III = 3,
    IV = 4,
    V = 5,
    VI = 6,
    VII = 7,
    VIII = 8,
    IX = 9,
    X = 10
}

-- Проверяем римская ли цифра
function isRomanNumeral(s)
    return romanNumerals[s] ~= nil
end

-- Римская => Арабская
function toArabic(roman)
    return romanNumerals[roman]
end

-- Арабская => Римская
function toRoman(arabic)
    if arabic < 1 or arabic > 10 then
        error("Результат д.б. в диапазоне от 1 до 10")
    end

    for key, value in pairs(romanNumerals) do
        if value == arabic then
            return key
        end
    end

    return ""
end

-- Калькуляруем
function calculate(parts)
    if #parts ~= 3 then
        error("Получено меньше данных чем ожидалось")
    end

    -- Получаем операнды и оператор
    local a, op, b = parts[1], parts[2], parts[3]

    -- Парсим операнды, в зависимости от их типа (арабские или римские числа)
    local intA = parseOperand(a)
    local intB = parseOperand(b)

    -- Сама операция
    local result = performOperation(intA, op, intB)

    -- Возвращаем результат
    if isRomanNumeral(a) then
        if result < 1 or result > 10 then
            error("Результат д.б. в диапазоне от 1 до 10")
        end
        return toRoman(result)
    else
        return tostring(result)
    end
end

-- Определяем тип числа и возвращаем его (число)
function parseOperand(operand)
    if isRomanNumeral(operand) then
        return toArabic(operand)
    else
        return tonumber(operand)
    end
end

-- Калькуляруем
function performOperation(a, operator, b)
    if operator == "+" then
        return a + b
    elseif operator == "-" then
        local result = a - b
        if result < 0 and isRomanNumeral(tostring(a)) then
            error("В римской системе нет отрицательных чисел")
        end
        return result
    elseif operator == "*" then
        return a * b
    elseif operator == "/" then
        if b == 0 then
            error("Деление на ноль")
        end
        return a // b
    else
        error("Недопустимая операция")
    end
end

-- Основной блок кода
print("Вывод:")
local status, result = pcall(function()
    io.write("Ввод: ")
    local input = io.read()
    local parts = {}
    for part in string.gmatch(input, "%S+") do
        table.insert(parts, part)
    end
    return calculate(parts)
end)

if status then
    print(result)
else
    print("Исключение: " .. result)
end
