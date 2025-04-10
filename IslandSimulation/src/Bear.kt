package island

class Bear : Animal("Bear", "🐻", 500.0, 5, 2, 80.0, 500.0) {
    override fun canEat(preyType: String) = listOf("Wolf", "Python", "Fox", "Horse", "Deer", "Rabbit", "Mouse", "Goat", "Sheep", "Boar").contains(preyType)
    override fun getFoodProbability(preyType: String) = when (preyType) {
        "Wolf" -> 15
        "Python" -> 80
        "Fox" -> 80
        "Horse" -> 40
        "Deer" -> 80
        "Rabbit" -> 80
        "Mouse" -> 90
        "Goat" -> 70
        "Sheep" -> 70
        "Boar" -> 50
        else -> 0
    }
}