fun main() {
    println("Введите два целых числа A и B:")
    val a = readLine()!!.toInt()
    val b = readLine()!!.toInt()
    val primes = (a..b).filter { num -> num > 1 && (2 until num).none { num % it == 0 } }
    println("Простые числа в диапазоне от $a до $b: $primes")
}
