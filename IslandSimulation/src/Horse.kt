package island

class Horse : Animal("Horse", "🐎", 400.0, 20, 4, 60.0, 400.0) {
    override fun canEat(preyType: String) = preyType == "Plant"
    override fun getFoodProbability(preyType: String) = 100
}