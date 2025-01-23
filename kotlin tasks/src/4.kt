fun main() {
    println("Введите целое число N:")
    val n = readLine()!!.toInt()
    val sum = (1..n).sum()
    println("Сумма от 1 до $n: $sum")
}
