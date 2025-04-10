package island

class Duck : Animal("Duck", "🦆", 1.0, 200, 4, 0.15, 1.0) {
    override fun canEat(preyType: String) = preyType == "Caterpillar"
    override fun getFoodProbability(preyType: String) = 90
}