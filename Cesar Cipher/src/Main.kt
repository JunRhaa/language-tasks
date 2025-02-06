import java.io.File

val russianLetterFrequencies = mapOf(
    'а' to 8.01f, 'о' to 10.97f, 'е' to 8.45f, 'и' to 7.35f, 'н' to 6.70f,
    'т' to 6.26f, 'с' to 5.47f, 'р' to 4.73f, 'в' to 4.54f, 'л' to 4.40f,
    'к' to 3.49f, 'м' to 3.21f, 'д' to 2.98f, 'п' to 2.81f, 'у' to 2.62f,
    'я' to 2.01f, 'ы' to 1.88f, 'з' to 1.65f, 'ь' to 1.59f, 'г' to 1.49f,
    'б' to 1.44f, 'ч' to 1.44f, 'й' to 1.21f, 'х' to 0.97f, 'ж' to 0.94f,
    'ш' to 0.73f, 'ю' to 0.64f, 'ц' to 0.48f, 'щ' to 0.36f, 'э' to 0.32f,
    'ф' to 0.26f, 'ъ' to 0.04f
)

fun main() {
    val menu = """
        1. Шифровка текста
        2. Расшифровка текста с помощью ключа
        3. Расшифровка текста методом brute force
        4. Расшифровка текста методом статистического анализа
        5. Выход
    """.trimIndent()

    while (true) {
        println(menu)
        print("Выберите режим работы: ")
        when (readlnOrNull()?.toIntOrNull()) {
            1 -> encryptFile()
            2 -> decryptFileWithKey()
            3 -> decryptFileBruteForce()
            4 -> decryptFileStatisticalAnalysis()
            5 -> return
            else -> println("Неверный выбор. Попробуйте снова.")
        }
    }
}

fun encryptFile() {
    println("Шифровка текста")
    val inputFile = getFileFromUser("Введите путь к исходному файлу: ")
    val outputFile = getOutputFilePath("Введите путь для зашифрованного файла: ")
    val shift = getValidShift("Введите ключ шифрования (сдвиг): ")

    try {
        val encryptedText = encrypt(inputFile.readText(), shift)
        File(outputFile).writeText(encryptedText)
        println("Текст успешно зашифрован и сохранен в $outputFile")
    } catch (e: Exception) {
        println("Ошибка при обработке файла: ${e.message}")
    }
}

fun decryptFileWithKey() {
    println("Расшифровка текста с помощью ключа")
    val inputFile = getFileFromUser("Введите путь к зашифрованному файлу: ")
    val outputFile = getOutputFilePath("Введите путь для расшифрованного файла: ")
    val shift = getValidShift("Введите ключ расшифрования (сдвиг): ")

    try {
        val decryptedText = decrypt(inputFile.readText(), shift)
        File(outputFile).writeText(decryptedText)
        println("Текст успешно расшифрован и сохранен в $outputFile")
    } catch (e: Exception) {
        println("Ошибка при обработке файла: ${e.message}")
    }
}

fun decryptFileBruteForce() {
    println("Расшифровка текста методом brute force")
    val inputFile = getFileFromUser("Введите путь к зашифрованному файлу: ")
    val encryptedText = inputFile.readText()

    for (shift in 0 until 32) {
        val decryptedText = decrypt(encryptedText, shift)
        println("Попытка расшифровки с ключом $shift:\n$decryptedText\n")
    }
}

fun decryptFileStatisticalAnalysis() {
    println("Расшифровка текста методом статистического анализа")
    val inputFile = getFileFromUser("Введите путь к зашифрованному файлу: ")
    val encryptedText = inputFile.readText().toLowerCase().filter { it.isLetterOrDigit() }

    val bestShift = findBestShift(encryptedText)
    val decryptedText = decrypt(inputFile.readText(), bestShift)

    val outputFile = getOutputFilePath("Введите путь для расшифрованного файла: ")
    File(outputFile).writeText(decryptedText)
    println("Текст успешно расшифрован и сохранен в $outputFile с ключом $bestShift")
}

fun encrypt(text: String, shift: Int): String {
    return text.map { char ->
        when {
            char.isLetter() -> {
                val base = if (char.isUpperCase()) 'А'.code else 'а'.code
                ((char.code - base + shift) % 32 + base).toChar()
            }
            else -> char
        }
    }.joinToString("")
}

fun decrypt(text: String, shift: Int): String {
    return encrypt(text, 32 - (shift % 32))
}

fun findBestShift(encryptedText: String): Int {
    var bestShift = 0
    var maxCorrelation = Float.MIN_VALUE

    for (shift in 0 until 32) {
        val decryptedText = decrypt(encryptedText, shift)
        val correlation = calculateCorrelation(decryptedText)
        if (correlation > maxCorrelation) {
            maxCorrelation = correlation
            bestShift = shift
        }
    }

    return bestShift
}

fun calculateCorrelation(text: String): Float {
    val letterCounts = mutableMapOf<Char, Int>()
    text.forEach { char ->
        if (char.isLetter()) {
            letterCounts[char] = letterCounts.getOrDefault(char, 0) + 1
        }
    }

    val totalLetters = letterCounts.values.sum().toFloat()
    var correlation = 0f

    russianLetterFrequencies.forEach { (letter, expectedFrequency) ->
        val actualFrequency = (letterCounts[letter] ?: 0) / totalLetters
        correlation += actualFrequency * expectedFrequency
    }

    return correlation
}

fun getFileFromUser(prompt: String): File {
    while (true) {
        print(prompt)
        val filePath = readlnOrNull()?.trim() ?: ""
        val file = File(filePath)
        if (file.exists()) return file
        println("Файл не найден. Попробуйте снова.")
    }
}

fun getOutputFilePath(prompt: String): String {
    print(prompt)
    return readlnOrNull()?.trim() ?: "output.txt"
}

fun getValidShift(prompt: String): Int {
    while (true) {
        print(prompt)
        val input = readlnOrNull()?.toIntOrNull()
        if (input != null && input in 0..31) return input
        println("Недопустимый ключ. Введите число от 0 до 31.")
    }
}