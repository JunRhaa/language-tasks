package island

class Rabbit : Animal("Rabbit", "🐇", 2.0, 150, 2, 0.45, 2.0) {
    override fun canEat(preyType: String) = preyType == "Plant"
    override fun getFoodProbability(preyType: String) = 100
}