package island

class Eagle : Animal("Eagle", "🦅", 6.0, 20, 3, 1.0, 6.0) {
    override fun canEat(preyType: String) = listOf("Rabbit", "Mouse", "Fox", "Duck").contains(preyType)
    override fun getFoodProbability(preyType: String) = when (preyType) {
        "Rabbit" -> 90
        "Mouse" -> 90
        "Fox" -> 10
        "Duck" -> 80
        else -> 0
    }
}