package island

class Caterpillar : Animal("Caterpillar", "🐛", 0.01, 1000, 0, 0.0, 0.01) {
    override fun canEat(preyType: String) = preyType == "Plant"
    override fun getFoodProbability(preyType: String) = 100
}