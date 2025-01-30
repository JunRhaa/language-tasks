import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.random.Random
import java.util.Locale
import java.time.YearMonth

fun main() {
    val currentDate = LocalDate.now()
    val currentTime = LocalTime.now()

    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    val formattedDateTime = "${currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))} ${currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))}"

    println(formattedDateTime)
}
fun daysUntilNewYear(): Long {
    val today = LocalDate.now()
    val newYear = LocalDate.of(today.year + 1, 1, 1)
    return java.time.temporal.ChronoUnit.DAYS.between(today, newYear)
}

fun main() {
    println("До нового года осталось ${daysUntilNewYear()} дней")
}
fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

fun main() {
    val year = 2024
    println("Год $year ${if (isLeapYear(year)) "високосный" else "не високосный"}")
}
fun countWeekendsInMonth(year: Int, month: Int): Int {
    val yearMonth = YearMonth.of(year, month)
    var weekendCount = 0

    for (day in yearMonth.atDay(1)..yearMonth.atEndOfMonth()) {
        if (day.dayOfWeek == DayOfWeek.SATURDAY || day.dayOfWeek == DayOfWeek.SUNDAY) {
            weekendCount++
        }
    }

    return weekendCount
}

fun main() {
    val year = 2023
    val month = 10
    println("Количество выходных в месяце: ${countWeekendsInMonth(year, month)}")
}
fun measureExecutionTime(action: () -> Unit): Long {
    val startTime = System.currentTimeMillis()
    action()
    val endTime = System.currentTimeMillis()
    return endTime - startTime
}

fun main() {
    val executionTime = measureExecutionTime {
        // Пример действия, которое мы хотим измерить
        for (i in 1..1_000_000) {
            // какой-то код
        }
    }
    println("Время выполнения: $executionTime мс")
}
fun parseAndFormatDate(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    val date = LocalDate.parse(dateString, inputFormatter)
    val newDate = date.plusDays(10)

    return newDate.format(outputFormatter)
}

fun main() {
    val dateString = "25-10-2023"
    println(parseAndFormatDate(dateString))
}
fun convertTimeZone(dateTime: ZonedDateTime, targetZoneId: String): ZonedDateTime {
    val targetZone = ZoneId.of(targetZoneId)
    return dateTime.withZoneSameInstant(targetZone)
}

fun main() {
    val utcDateTime = ZonedDateTime.now(ZoneId.of("UTC"))
    println("UTC Time: $utcDateTime")

    val moscowDateTime = convertTimeZone(utcDateTime, "Europe/Moscow")
    println("Moscow Time: $moscowDateTime")
}
fun calculateAge(birthDate: LocalDate): Int {
    val today = LocalDate.now()
    val period = Period.between(birthDate, today)
    return period.years
}

fun main() {
    val birthDate = LocalDate.of(1990, 5, 15)
    println("Возраст: ${calculateAge(birthDate)} лет")
}
fun printCalendar(year: Int, month: Int) {
    val yearMonth = YearMonth.of(year, month)
    var currentDate = yearMonth.atDay(1)

    while (currentDate.monthValue == month) {
        val dayOfWeek = currentDate.dayOfWeek
        val isWeekend = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
        val dayType = if (isWeekend) "Выходной" else "Рабочий"

        println("${currentDate.dayOfMonth} - ${dayOfWeek.name} - $dayType")

        currentDate = currentDate.plusDays(1)
    }
}

fun main() {
    val year = 2023
    val month = 10
    printCalendar(year, month)
}
fun generateRandomDate(start: LocalDate, end: LocalDate): LocalDate {
    val daysBetween = ChronoUnit.DAYS.between(start, end)
    val randomDays = Random.nextLong(0, daysBetween)
    return start.plusDays(randomDays)
}

fun main() {
    val startDate = LocalDate.of(2023, 1, 1)
    val endDate = LocalDate.of(2023, 12, 31)
    val randomDate = generateRandomDate(startDate, endDate)
    println("Случайная дата: $randomDate")
}
fun timeUntilEvent(eventDateTime: LocalDateTime): String {
    val now = LocalDateTime.now()
    val duration = Duration.between(now, eventDateTime)

    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60

    return "Осталось $hours часов, $minutes минут, $seconds секунд"
}

fun main() {
    val eventDateTime = LocalDateTime.of(2024, 1, 1, 0, 0)
    println(timeUntilEvent(eventDateTime))
}
fun calculateWorkingHours(start: LocalDateTime, end: LocalDateTime): Long {
    var current = start
    var totalHours = 0L

    while (!current.isAfter(end)) {
        if (current.dayOfWeek != DayOfWeek.SATURDAY && current.dayOfWeek != DayOfWeek.SUNDAY) {
            val nextDayStart = current.toLocalDate().plusDays(1).atStartOfDay()
            val endOfDay = if (nextDayStart.isBefore(end)) nextDayStart else end
            totalHours += Duration.between(current, endOfDay).toHours()
        }
        current = current.toLocalDate().plusDays(1).atStartOfDay()
    }

    return totalHours
}
fun formatDateWithLocale(date: LocalDate, locale: Locale): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", locale)
    return date.format(formatter)
}

fun main() {
    val date = LocalDate.of(2023, 10, 25)
    println("На русском: " + formatDateWithLocale(date, Locale("ru")))
    println("На английском: " + formatDateWithLocale(date, Locale.ENGLISH))
}
fun getDayOfWeekInRussian(date: LocalDate): String {
    val daysOfWeek = listOf("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье")
    return daysOfWeek[date.dayOfWeek.value - 1]
}

fun main() {
    val date = LocalDate.of(2023, 10, 25)
    println(getDayOfWeekInRussian(date))
}