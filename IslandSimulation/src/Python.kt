package island

class Python : Animal("Python", "🐍", 15.0, 30, 1, 3.0, 15.0) {
    override fun canEat(preyType: String) = listOf("Rabbit", "Mouse").contains(preyType)
    override fun getFoodProbability(preyType: String) = when (preyType) {
        "Rabbit" -> 20
        "Mouse" -> 40
        else -> 0
    }
}