fun main() {
    println("Введите целое число:")
    val number = readLine()!!.toInt()
    if (number % 2 == 0) {
        println("Четное число")
    } else {
        println("Нечетное число")
    }
}
