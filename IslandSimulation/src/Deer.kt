package island

class Deer : Animal("Deer", "🦌", 300.0, 20, 4, 50.0, 300.0) {
    override fun canEat(preyType: String) = preyType == "Plant"
    override fun getFoodProbability(preyType: String) = 100
}