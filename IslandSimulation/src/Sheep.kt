package island

class Sheep : Animal("Sheep", "🐑", 70.0, 140, 3, 15.0, 70.0) {
    override fun canEat(preyType: String) = preyType == "Plant"
    override fun getFoodProbability(preyType: String) = 100
}