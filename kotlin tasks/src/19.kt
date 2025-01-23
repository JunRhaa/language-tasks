fun main() {
    println("Введите строку, состоящую из нескольких слов:")
    val input = readLine()!!
    val reversedWords = input.split(" ").reversed().joinToString(" ")
    println("Перестановка слов: $reversedWords")
}
