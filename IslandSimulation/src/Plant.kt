package island

class Plant : Animal("Plant", "🌱", 1.0, 200, 0, 0.0, 1.0) {
    override fun canEat(preyType: String) = false
    override fun getFoodProbability(preyType: String) = 0
}