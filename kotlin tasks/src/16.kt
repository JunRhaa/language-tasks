fun main() {
    println("Введите размер массива:")
    val size = readLine()!!.toInt()
    val numbers = mutableListOf<Int>()
    println("Введите элементы массива:")
    repeat(size) {
        numbers.add(readLine()!!.toInt())
    }
    val positives = numbers.count { it > 0 }
    val negatives = numbers.count { it < 0 }
    println("Количество положительных чисел: $positives, отрицательных чисел: $negatives")
}
