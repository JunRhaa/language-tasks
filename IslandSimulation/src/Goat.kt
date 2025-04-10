package island

class Goat : Animal("Goat", "🐐", 60.0, 140, 3, 10.0, 60.0) {
    override fun canEat(preyType: String) = preyType == "Plant"
    override fun getFoodProbability(preyType: String) = 100
}