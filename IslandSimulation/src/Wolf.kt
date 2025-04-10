package island

class Wolf : Animal("Wolf", "🐺", 50.0, 30, 3, 8.0, 50.0) {
    override fun canEat(preyType: String) = listOf("Rabbit", "Deer", "Sheep", "Boar", "Duck").contains(preyType)
    override fun getFoodProbability(preyType: String) = when (preyType) {
        "Rabbit" -> 60
        "Deer" -> 15
        "Sheep" -> 70
        "Boar" -> 15
        "Duck" -> 40
        else -> 0
    }
}