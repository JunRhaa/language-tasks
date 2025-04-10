package island

class Fox : Animal("Fox", "🦊", 8.0, 30, 2, 2.0, 8.0) {
    override fun canEat(preyType: String) = listOf("Rabbit", "Mouse", "Caterpillar").contains(preyType)
    override fun getFoodProbability(preyType: String) = when (preyType) {
        "Rabbit" -> 70
        "Mouse" -> 90
        "Caterpillar" -> 40
        else -> 0
    }
}