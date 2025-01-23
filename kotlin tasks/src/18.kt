fun main() {
    println("Введите строку:")
    val input = readLine()!!
    val vowels = "аеёиоуыэюяАЕЁИОУЫЭЮЯ"
    val consonants = input.filter { it.isLetter() && !vowels.contains(it) }
    val vowelCount = input.count { it in vowels }
    println("Количество гласных: $vowelCount, согласных: ${consonants.length}")
}
