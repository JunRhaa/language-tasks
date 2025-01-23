fun main() {
    println("Введите два целых числа A и B:")
    val a = readLine()!!.toInt()
    val b = readLine()!!.toInt()
    val sum = (a..b).filter { it % 2 == 0 }.sum()
    println("Сумма четных чисел от $a до $b: $sum")
}
