fun main() {
    println("Введите целое число N:")
    val n = readLine()!!.toInt()
    for (i in n downTo 1) {
        print("$i ")
    }
}
