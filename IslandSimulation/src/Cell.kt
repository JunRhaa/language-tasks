package island

data class Cell(val x: Int, val y: Int) {
    val animals = mutableListOf<Animal>()
    var plants: Int = 0

    fun addAnimal(animal: Animal) {
        if (animals.count { it.type == animal.type } < animal.maxCount) {
            animals.add(animal)
        }
    }

    fun removeAnimal(animal: Animal) {
        animals.remove(animal)
    }
}