fun main() {
    println("Введите целое число:")
    val number = readLine()!!.toInt()
    val digits = number.toString().map { it.toString().toInt() }
    val power = digits.size
    val isArmstrong = digits.sumOf { Math.pow(it.toDouble(), power.toDouble()).toInt() } == number
    println(if (isArmstrong) "$number - число Армстронга" else "$number - не число Армстронга")
}
