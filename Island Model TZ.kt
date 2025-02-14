import java.util.concurrent.*
import kotlin.random.Random

data class SimulationParameters(
    val islandSize: Pair<Int, Int> = 100 to 20,
    val plantGrowthInterval: Long = 1000,
    val animalLifecycleInterval: Long = 500,
    val maxAnimalsPerCell: Int = 200,
    val initialAnimals: Map<String, Int> = mapOf("Wolf" to 10, "Rabbit" to 50)
)

abstract class Animal(val name: String, val weight: Double, val speed: Int, val foodRequirement: Double) {
    abstract fun eat(food: Any?): Boolean
    abstract fun reproduce(partner: Animal?): Boolean
    open fun move(): Pair<Int, Int> {
        return Random.nextInt(-speed, speed + 1) to Random.nextInt(-speed, speed + 1)
    }
}

open class Carnivore(name: String, weight: Double, speed: Int, foodRequirement: Double) : Animal(name, weight, speed, foodRequirement) {
    override fun eat(food: Any?): Boolean {
        if (food is Herbivore) {
            val probability = getEatProbability(this, food)
            return Random.nextDouble(0.0, 100.0) <= probability
        }
        return false
    }

    private fun getEatProbability(carnivore: Carnivore, herbivore: Herbivore): Double {
        return when (carnivore.name) {
            "Wolf" -> when (herbivore.name) {
                "Rabbit" -> 60.0
                else -> 0.0
            }
            else -> 0.0
        }
    }

    override fun reproduce(partner: Animal?): Boolean {
        if (partner != null && partner::class == this::class) {
            println("${this.name} reproduces with ${partner.name}")
            return true
        }
        return false
    }
}

open class Herbivore(name: String, weight: Double, speed: Int, foodRequirement: Double) : Animal(name, weight, speed, foodRequirement) {
    override fun eat(food: Any?): Boolean {
        if (food is Plant) {
            return true
        }
        return false
    }

    override fun reproduce(partner: Animal?): Boolean {
        if (partner != null && partner::class == this::class) {
            println("${this.name} reproduces with ${partner.name}")
            return true
        }
        return false
    }
}
class Wolf : Carnivore("Wolf", 50.0, 3, 8.0)
class Rabbit : Herbivore("Rabbit", 2.0, 2, 0.45)

class Plant(val growthRate: Int = 1)

class Location(var plants: MutableList<Plant> = mutableListOf(), var animals: MutableList<Animal> = mutableListOf()) {
    fun addAnimal(animal: Animal) {
        animals.add(animal)
    }

    fun removeAnimal(animal: Animal) {
        animals.remove(animal)
    }

    fun growPlants() {
        repeat(Random.nextInt(1, 5)) { plants.add(Plant()) }
    }
}

class Island(val size: Pair<Int, Int>, val params: SimulationParameters) {
    private val grid = Array(size.first) { Array(size.second) { Location() } }
    private val executor = Executors.newScheduledThreadPool(10)

    init {
        initializeAnimals()
        startSimulation()
    }

    private fun initializeAnimals() {
        params.initialAnimals.forEach { (animalName, count) ->
            repeat(count) {
                val x = Random.nextInt(0, size.first)
                val y = Random.nextInt(0, size.second)
                val animal = when (animalName) {
                    "Wolf" -> Wolf()
                    "Rabbit" -> Rabbit()
                    else -> throw IllegalArgumentException("Unknown animal type")
                }
                grid[x][y].addAnimal(animal)
            }
        }
    }

    private fun startSimulation() {
        executor.scheduleAtFixedRate({ growPlants() }, 0, params.plantGrowthInterval, TimeUnit.MILLISECONDS)
        executor.scheduleAtFixedRate({ lifecycleAnimals() }, 0, params.animalLifecycleInterval, TimeUnit.MILLISECONDS)
    }

    private fun growPlants() {
        for (row in grid) {
            for (location in row) {
                location.growPlants()
            }
        }
        printStatistics()
    }

    private fun lifecycleAnimals() {
        for (row in grid) {
            for (location in row) {
                val animalsCopy = location.animals.toMutableList()
                for (animal in animalsCopy) {
                    animal.move()
                    animal.eat(location.animals.find { it != animal })
                    if (Random.nextDouble() < 0.1) {
                        animal.reproduce(location.animals.find { it != animal && it::class == animal::class })
                    }
                }
            }
        }
        printStatistics()
    }

    private fun printStatistics() {
        println("Island Statistics:")
        println("Total Plants: ${grid.sumOf { it.sumOf { it.plants.size } }}")
        println("Total Animals: ${grid.sumOf { it.sumOf { it.animals.size } }}")
    }
}

fun main() {
    val params = SimulationParameters()
    val island = Island(params.islandSize, params)
}