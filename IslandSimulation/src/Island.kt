package island

import kotlin.random.Random

class Island {
    val grid = Array(Config.ISLAND_HEIGHT) { y ->
        Array(Config.ISLAND_WIDTH) { x -> Cell(x, y) }
    }

    init {
        Config.INITIAL_ANIMALS.forEach { (type, count) ->
            repeat(count) {
                val x = Random.nextInt(Config.ISLAND_WIDTH)
                val y = Random.nextInt(Config.ISLAND_HEIGHT)
                val cell = grid[y][x]
                when (type) {
                    "Wolf" -> cell.addAnimal(Wolf())
                    "Python" -> cell.addAnimal(Python())
                    "Fox" -> cell.addAnimal(Fox())
                    "Bear" -> cell.addAnimal(Bear())
                    "Eagle" -> cell.addAnimal(Eagle())
                    "Horse" -> cell.addAnimal(Horse())
                    "Deer" -> cell.addAnimal(Deer())
                    "Rabbit" -> cell.addAnimal(Rabbit())
                    "Mouse" -> cell.addAnimal(Mouse())
                    "Goat" -> cell.addAnimal(Goat())
                    "Sheep" -> cell.addAnimal(Sheep())
                    "Boar" -> cell.addAnimal(Boar())
                    "Bison" -> cell.addAnimal(Bison())
                    "Duck" -> cell.addAnimal(Duck())
                    "Caterpillar" -> cell.addAnimal(Caterpillar())
                    "Plant" -> cell.plants++
                }
            }
        }
    }

    fun simulate() {
        grid.flatten().forEach { cell ->
            cell.animals.toList().forEach { animal ->
                if (!animal.isDead()) {
                    animal.eat(cell)
                    animal.move(cell, this)
                    animal.loseEnergy()
                    if (animal.canBreed()) {
                        if (cell.animals.count { it.type == animal.type } < animal.maxCount) {
                            val newAnimal = when (animal.type) {
                                "Wolf" -> Wolf()
                                "Python" -> Python()
                                "Fox" -> Fox()
                                "Bear" -> Bear()
                                "Eagle" -> Eagle()
                                "Horse" -> Horse()
                                "Deer" -> Deer()
                                "Rabbit" -> Rabbit()
                                "Mouse" -> Mouse()
                                "Goat" -> Goat()
                                "Sheep" -> Sheep()
                                "Boar" -> Boar()
                                "Bison" -> Bison()
                                "Duck" -> Duck()
                                "Caterpillar" -> Caterpillar()
                                else -> null
                            }
                            newAnimal?.let { cell.addAnimal(it) }
                            animal.energy /= 2
                        }
                    }
                } else {
                    cell.removeAnimal(animal)
                }
            }
        }

        grid.flatten().forEach { cell ->
            if (cell.plants < 200 && Random.nextInt(100) < 20) {
                cell.plants++
            }
        }
    }

    fun getStats(): Map<String, Int> {
        val stats = mutableMapOf<String, Int>()
        grid.flatten().forEach { cell ->
            cell.animals.forEach {
                stats[it.type] = stats.getOrDefault(it.type, 0) + 1
            }
            stats["Plant"] = stats.getOrDefault("Plant", 0) + cell.plants
        }
        return stats
    }
}