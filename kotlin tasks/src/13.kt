fun main() {
    println("Введите строку:")
    val input = readLine()!!
    val isPalindrome = input == input.reversed()
    println(if (isPalindrome) "$input - палиндром" else "$input - не палиндром")
}
