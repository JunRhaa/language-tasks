fun main() {
    println("Введите целое число N:")
    val n = readLine()!!.toInt()
    val factorial = (1..n).reduce { acc, i -> acc * i }
    println("Факториал $n: $factorial")
}
