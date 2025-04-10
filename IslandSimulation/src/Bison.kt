package island

class Bison : Animal("Bison", "🐃", 700.0, 10, 3, 100.0, 700.0) {
    override fun canEat(preyType: String) = preyType == "Plant"
    override fun getFoodProbability(preyType: String) = 100
}