fun main() {
    println("Введите три целых числа:")
    val numbers = List(3) { readLine()!!.toInt() }
    println("Минимальное число: ${numbers.minOrNull()}")
}
