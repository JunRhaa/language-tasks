class Database private constructor() {
    companion object {
        private var instance: Database? = null

        fun getInstance(): Database {
            if (instance == null) {
                instance = Database()
                println("Создано новое подключение к базе данных")
            }
            return instance!!
        }
    }

    fun connect() {
        println("Подключение к базе данных установлено")
    }
}

fun main() {
    val db1 = Database.getInstance()
    db1.connect()

    val db2 = Database.getInstance()
    db2.connect()

    println(if (db1 === db2) "db1 и db2 ссылаются на один и тот же объект" else "Разные объекты")
}
class Logger private constructor() {
    companion object {
        private var instance: Logger? = null
        private val logs = mutableListOf<String>()

        fun getInstance(): Logger {
            if (instance == null) {
                instance = Logger()
            }
            return instance!!
        }
    }

    fun log(message: String) {
        logs.add(message)
        println("Добавлен лог: $message")
    }

    fun printLogs() {
        println("Все логи:")
        for (log in logs) {
            println(log)
        }
    }
}

fun main() {
    val logger1 = Logger.getInstance()
    logger1.log("Запуск системы")

    val logger2 = Logger.getInstance()
    logger2.log("Пользователь авторизован")

    logger1.printLogs()
}
enum class OrderStatus {
    NEW, IN_PROGRESS, DELIVERED, CANCELLED
}

class Order(private var status: OrderStatus) {
    fun changeStatus(newStatus: OrderStatus) {
        when (status) {
            OrderStatus.DELIVERED -> if (newStatus == OrderStatus.CANCELLED) {
                println("Нельзя отменить доставленный заказ")
                return
            }
            else -> {}
        }

        status = newStatus
        println("Статус заказа изменен на $status")
    }

    fun getStatus(): OrderStatus {
        return status
    }
}

fun main() {
    val order = Order(OrderStatus.NEW)
    println("Текущий статус: ${order.getStatus()}")

    order.changeStatus(OrderStatus.IN_PROGRESS)
    println("Текущий статус: ${order.getStatus()}")

    order.changeStatus(OrderStatus.DELIVERED)
    println("Текущий статус: ${order.getStatus()}")

    order.changeStatus(OrderStatus.CANCELLED) // Нельзя отменить доставленный заказ
}
enum class Season {
    WINTER, SPRING, SUMMER, AUTUMN
}

fun getSeasonName(season: Season): String {
    return when (season) {
        Season.WINTER -> "Зима"
        Season.SPRING -> "Весна"
        Season.SUMMER -> "Лето"
        Season.AUTUMN -> "Осень"
    }
}

fun main() {
    val season = Season.SUMMER
    println("Сезон: ${getSeasonName(season)}")

    val allSeasons = listOf(Season.WINTER, Season.SPRING, Season.SUMMER, Season.AUTUMN)
    for (s in allSeasons) {
        println("${s.name} - ${getSeasonName(s)}")
    }
}