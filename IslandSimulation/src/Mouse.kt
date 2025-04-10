package island

class Mouse : Animal("Mouse", "🐁", 0.05, 500, 1, 0.01, 0.05) {
    override fun canEat(preyType: String) = preyType == "Plant"
    override fun getFoodProbability(preyType: String) = 100
}