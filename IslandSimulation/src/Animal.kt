package island

import java.util.concurrent.ThreadLocalRandom

abstract class Animal(
    val type: String,
    val emoji: String,
    val weight: Double,
    val maxCount: Int,
    val speed: Int,
    val foodRequired: Double,
    var energy: Double
) {
    companion object {
        val random = ThreadLocalRandom.current()
    }

    abstract fun canEat(preyType: String): Boolean
    abstract fun getFoodProbability(preyType: String): Int

    open fun eat(cell: Cell) {
        val availableFood = cell.animals.filter { canEat(it.type) } + if (canEat("Plant")) List(cell.plants) { Plant() } else emptyList()
        if (availableFood.isNotEmpty()) {
            val prey = availableFood.random()
            if (random.nextInt(100) < getFoodProbability(preyType = prey.type)) {
                energy = (energy + prey.weight).coerceAtMost(foodRequired * 2)
                if (prey is Animal) cell.removeAnimal(prey)
                else cell.plants--
            }
        }
    }

    open fun move(from: Cell, island: Island) {
        if (speed == 0) return
        val directions = listOf(
            -1 to 0, 1 to 0, 0 to -1, 0 to 1
        ).shuffled().take(speed)
        var currentCell = from
        directions.forEach { (dx, dy) ->
            val newX = (currentCell.x + dx).coerceIn(0, Config.ISLAND_WIDTH - 1)
            val newY = (currentCell.y + dy).coerceIn(0, Config.ISLAND_HEIGHT - 1)
            val newCell = island.grid[newY][newX]
            if (newCell.animals.count { it.type == type } < maxCount) {
                currentCell.removeAnimal(this)
                newCell.addAnimal(this)
                currentCell = newCell
            }
        }
    }

    open fun canBreed(): Boolean = energy >= foodRequired * 0.8
    open fun loseEnergy() { energy = (energy - foodRequired * 0.2).coerceAtLeast(0.0) }
    open fun isDead(): Boolean = energy <= 0
}