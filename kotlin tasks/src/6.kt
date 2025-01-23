fun main() {
    println("Введите целое число:")
    val number = readLine()!!.toInt()
    val isPrime = number > 1 && (2 until number).none { number % it == 0 }
    println(if (isPrime) "$number - простое число" else "$number - не простое число")
}
