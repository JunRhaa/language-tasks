package island

class Boar : Animal("Boar", "🐗", 400.0, 50, 2, 50.0, 400.0) {
    override fun canEat(preyType: String) = listOf("Plant", "Mouse").contains(preyType)
    override fun getFoodProbability(preyType: String) = when (preyType) {
        "Plant" -> 100
        "Mouse" -> 90
        else -> 0
    }
}