fun main() {
    println("Введите целое число:")
    val number = readLine()!!
    val sumOfDigits = number.map { it.toString().toInt() }.sum()
    println("Сумма цифр: $sumOfDigits")
}
