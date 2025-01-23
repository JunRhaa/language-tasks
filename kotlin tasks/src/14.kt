fun main() {
    println("Введите размер массива:")
    val size = readLine()!!.toInt()
    val numbers = mutableListOf<Int>()
    println("Введите элементы массива:")
    repeat(size) {
        numbers.add(readLine()!!.toInt())
    }
    println("Максимальное число в массиве: ${numbers.maxOrNull()}")
}
