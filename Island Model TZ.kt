import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.math.absoluteValue

object Config {
    const val ISLAND_WIDTH = 100
    const val ISLAND_HEIGHT = 20
    const val SIMULATION_DELAY_MS = 1000L
    val INITIAL_ANIMALS = mapOf(
        Wolf::class to 10,
        Python::class to 5,
        Fox::class to 15,
        Bear::class to 3,
        Eagle::class to 8,
        Horse::class to 50,
        Deer::class to 50,
        Rabbit::class to 200,
        Mouse::class to 500,
        Goat::class to 100,
        Sheep::class to 100,
        Boar::class to 25,
        Buffalo::class to 5,
        Duck::class to 150,
        Caterpillar::class to 1000
    )
    const val MAX_ENTITIES_PER_CELL = 100
}

sealed class Entity(val cell: Cell) {
    abstract val symbol: String
}

abstract class Animal(cell: Cell) : Entity(cell), Runnable {
    abstract val maxSatiety: Double
    abstract val breedThreshold: Double
    abstract val moveProbability: Double
    abstract val speed: Int
    abstract val foodProbabilities: Map<Class<out Animal>, Int>
    abstract val plantProbability: Int

    val satietyLock = ReentrantLock()
    var satiety: Double = maxSatiety * 0.5
    val isAlive: Boolean get() = satiety > 0.0

    override fun run() {
        if (!isAlive) return die()

        try {
            satietyLock.lock()
            satiety -= maxSatiety * 0.1
            if (satiety <= 0) die()
            else {
                eat()
                move()
                breed()
            }
        } finally {
            satietyLock.unlock()
        }
    }

    open fun eat() {
        val availableFood = cell.entities.filter { it != this && isEdible(it) }
        if (availableFood.isEmpty()) return

        val food = availableFood.random()
        val probability = when {
            food is Animal -> foodProbabilities[food::class.java]?.div(100.0) ?: 0.0
            food is Plant -> plantProbability.toDouble() / 100
            else -> 0.0
        }

        if (probability > 0 && ThreadLocalRandom.current().nextDouble() < probability) {
            satiety = (satiety + getFoodValue(food)).coerceAtMost(maxSatiety)
            cell.removeEntity(food)
        }
    }

    abstract fun getFoodValue(food: Entity): Double

    private fun isEdible(entity: Entity): Boolean {
        return when (entity) {
            is Animal -> foodProbabilities.containsKey(entity::class.java)
            is Plant -> plantProbability > 0
            else -> false
        }
    }

    private fun move() {
        if (ThreadLocalRandom.current().nextDouble() > moveProbability) return

        val directions = cell.getAdjacentCells(1)
            .filter { it != cell }
            .filter { (it.x - cell.x).absoluteValue <= speed && (it.y - cell.y).absoluteValue <= speed }

        if (directions.isNotEmpty()) {
            val newCell = directions.random()
            cell.removeEntity(this)
            newCell.addEntity(this)
        }
    }

    private fun breed() {
        if (satiety < breedThreshold) return

        val potentialPartners = cell.entities.filter { it::class == this::class && it !== this }
        if (potentialPartners.isNotEmpty()) {
            satiety *= 0.5
            val newAnimal = this::class.java.getConstructor(Cell::class.java).newInstance(cell)
            cell.addEntity(newAnimal)
        }
    }

    open fun die() {
        cell.removeEntity(this)
    }
}

class Wolf(cell: Cell) : Animal(cell) {
    override val symbol = "🐺"
    override val maxSatiety = 8.0
    override val breedThreshold = 6.0
    override val moveProbability = 0.7
    override val speed = 3
    override val foodProbabilities: Map<Class<out Animal>, Int> = mapOf(
        Rabbit::class.java to 60,
        Mouse::class.java to 80,
        Deer::class.java to 15,
        Goat::class.java to 60,
        Sheep::class.java to 70,
        Boar::class.java to 15,
        Buffalo::class.java to 10,
        Duck::class.java to 40
    )
    override val plantProbability = 0

    override fun getFoodValue(food: Entity) = when (food) {
        is Rabbit -> 2.0
        is Mouse -> 0.5
        is Deer -> 15.0
        is Goat -> 5.0
        is Sheep -> 7.0
        is Boar -> 25.0
        is Buffalo -> 35.0
        is Duck -> 1.0
        else -> 0.0
    }
}

class Python(cell: Cell) : Animal(cell) {
    override val symbol = "🐍"
    override val maxSatiety = 3.0
    override val breedThreshold = 2.0
    override val moveProbability = 0.3
    override val speed = 1
    override val foodProbabilities: Map<Class<out Animal>, Int> = mapOf(
        Fox::class.java to 15,
        Rabbit::class.java to 20,
        Mouse::class.java to 40
    )
    override val plantProbability = 0

    override fun getFoodValue(food: Entity) = when (food) {
        is Fox -> 1.5
        is Rabbit -> 1.0
        is Mouse -> 0.2
        else -> 0.0
    }
}

class Fox(cell: Cell) : Animal(cell) {
    override val symbol = "🦊"
    override val maxSatiety = 2.0
    override val breedThreshold = 1.5
    override val moveProbability = 0.6
    override val speed = 2
    override val foodProbabilities: Map<Class<out Animal>, Int> = mapOf(
        Rabbit::class.java to 70,
        Mouse::class.java to 90,
        Duck::class.java to 60,
        Caterpillar::class.java to 40
    )
    override val plantProbability = 0

    override fun getFoodValue(food: Entity) = when (food) {
        is Rabbit -> 1.0
        is Mouse -> 0.2
        is Duck -> 0.5
        is Caterpillar -> 0.1
        else -> 0.0
    }
}

class Bear(cell: Cell) : Animal(cell) {
    override val symbol = "🐻"
    override val maxSatiety = 80.0
    override val breedThreshold = 60.0
    override val moveProbability = 0.5
    override val speed = 2
    override val foodProbabilities: Map<Class<out Animal>, Int> = mapOf(
        Horse::class.java to 40,
        Deer::class.java to 80,
        Rabbit::class.java to 80,
        Mouse::class.java to 90,
        Goat::class.java to 70,
        Sheep::class.java to 70,
        Boar::class.java to 50,
        Buffalo::class.java to 20,
        Duck::class.java to 10
    )
    override val plantProbability = 0

    override fun getFoodValue(food: Entity) = when (food) {
        is Horse -> 60.0
        is Deer -> 50.0
        is Rabbit -> 0.45
        is Mouse -> 0.01
        is Goat -> 10.0
        is Sheep -> 15.0
        is Boar -> 50.0
        is Buffalo -> 100.0
        is Duck -> 0.15
        else -> 0.0
    }
}

class Eagle(cell: Cell) : Animal(cell) {
    override val symbol = "🦅"
    override val maxSatiety = 1.0
    override val breedThreshold = 0.8
    override val moveProbability = 0.7
    override val speed = 3
    override val foodProbabilities: Map<Class<out Animal>, Int> = mapOf(
        Fox::class.java to 10,
        Rabbit::class.java to 90,
        Mouse::class.java to 90,
        Duck::class.java to 80
    )
    override val plantProbability = 0

    override fun getFoodValue(food: Entity) = when (food) {
        is Fox -> 0.5
        is Rabbit -> 0.45
        is Mouse -> 0.01
        is Duck -> 0.15
        else -> 0.0
    }
}

class Horse(cell: Cell) : Animal(cell) {
    override val symbol = "🐎"
    override val maxSatiety = 60.0
    override val breedThreshold = 45.0
    override val moveProbability = 0.6
    override val speed = 4
    override val foodProbabilities: Map<Class<out Animal>, Int> = emptyMap()
    override val plantProbability = 100

    override fun getFoodValue(food: Entity) = (food as? Plant)?.let { 1.0 } ?: 0.0
}

class Deer(cell: Cell) : Animal(cell) {
    override val symbol = "🦌"
    override val maxSatiety = 50.0
    override val breedThreshold = 37.5
    override val moveProbability = 0.7
    override val speed = 4
    override val foodProbabilities: Map<Class<out Animal>, Int> = emptyMap()
    override val plantProbability = 100

    override fun getFoodValue(food: Entity) = (food as? Plant)?.let { 1.0 } ?: 0.0
}

class Rabbit(cell: Cell) : Animal(cell) {
    override val symbol = "🐇"
    override val maxSatiety = 0.45
    override val breedThreshold = 0.3
    override val moveProbability = 0.8
    override val speed = 2
    override val foodProbabilities: Map<Class<out Animal>, Int> = emptyMap()
    override val plantProbability = 100

    override fun getFoodValue(food: Entity) = (food as? Plant)?.let { 0.1 } ?: 0.0
}

class Mouse(cell: Cell) : Animal(cell) {
    override val symbol = "🐁"
    override val maxSatiety = 0.01
    override val breedThreshold = 0.0075
    override val moveProbability = 0.9
    override val speed = 1
    override val foodProbabilities: Map<Class<out Animal>, Int> = emptyMap()
    override val plantProbability = 100

    override fun getFoodValue(food: Entity) = (food as? Plant)?.let { 0.001 } ?: 0.0
}

class Goat(cell: Cell) : Animal(cell) {
    override val symbol = "🐐"
    override val maxSatiety = 10.0
    override val breedThreshold = 7.5
    override val moveProbability = 0.6
    override val speed = 3
    override val foodProbabilities: Map<Class<out Animal>, Int> = emptyMap()
    override val plantProbability = 100

    override fun getFoodValue(food: Entity) = (food as? Plant)?.let { 0.2 } ?: 0.0
}

class Sheep(cell: Cell) : Animal(cell) {
    override val symbol = "🐑"
    override val maxSatiety = 15.0
    override val breedThreshold = 11.25
    override val moveProbability = 0.5
    override val speed = 3
    override val foodProbabilities: Map<Class<out Animal>, Int> = emptyMap()
    override val plantProbability = 100

    override fun getFoodValue(food: Entity) = (food as? Plant)?.let { 0.3 } ?: 0.0
}

class Boar(cell: Cell) : Animal(cell) {
    override val symbol = "🐗"
    override val maxSatiety = 50.0
    override val breedThreshold = 37.5
    override val moveProbability = 0.4
    override val speed = 2
    override val foodProbabilities: Map<Class<out Animal>, Int> = mapOf(Mouse::class.java to 50)
    override val plantProbability = 100

    override fun getFoodValue(food: Entity) = when (food) {
        is Plant -> 1.0
        is Mouse -> 0.01
        else -> 0.0
    }
}

class Buffalo(cell: Cell) : Animal(cell) {
    override val symbol = "🐃"
    override val maxSatiety = 100.0
    override val breedThreshold = 75.0
    override val moveProbability = 0.3
    override val speed = 3
    override val foodProbabilities: Map<Class<out Animal>, Int> = emptyMap()
    override val plantProbability = 100

    override fun getFoodValue(food: Entity) = (food as? Plant)?.let { 2.0 } ?: 0.0
}

class Duck(cell: Cell) : Animal(cell) {
    override val symbol = "🦆"
    override val maxSatiety = 0.15
    override val breedThreshold = 0.1
    override val moveProbability = 0.9
    override val speed = 4
    override val foodProbabilities: Map<Class<out Animal>, Int> = mapOf(Caterpillar::class.java to 90)
    override val plantProbability = 100

    override fun getFoodValue(food: Entity) = when (food) {
        is Plant -> 0.05
        is Caterpillar -> 0.02
        else -> 0.0
    }
}

class Caterpillar(cell: Cell) : Animal(cell) {
    override val symbol = "🐛"
    override val maxSatiety = 0.0
    override val breedThreshold = 0.0
    override val moveProbability = 0.0
    override val speed = 0
    override val foodProbabilities: Map<Class<out Animal>, Int> = emptyMap()
    override val plantProbability = 100

    override fun getFoodValue(food: Entity) = (food as? Plant)?.let { 0.001 } ?: 0.0
}

class Plant(cell: Cell) : Entity(cell) {
    override val symbol = "🌱"
    var growthStage = 0
        private set

    fun grow() {
        growthStage = (growthStage + 1) % 3
    }
}

class Cell(val x: Int, val y: Int) {
    lateinit var island: Array<Array<Cell>>
    val entities = mutableListOf<Entity>()
    private val lock = ReentrantLock()

    fun addEntity(entity: Entity) {
        lock.lock()
        try {
            if (entities.size < Config.MAX_ENTITIES_PER_CELL) {
                entities.add(entity)
            }
        } finally {
            lock.unlock()
        }
    }

    fun removeEntity(entity: Entity) {
        lock.lock()
        try {
            entities.remove(entity)
        } finally {
            lock.unlock()
        }
    }

    fun getAdjacentCells(radius: Int = 1): List<Cell> {
        val minX = (x - radius).coerceAtLeast(0)
        val maxX = (x + radius).coerceAtMost(Config.ISLAND_WIDTH - 1)
        val minY = (y - radius).coerceAtLeast(0)
        val maxY = (y + radius).coerceAtMost(Config.ISLAND_HEIGHT - 1)

        return island.sliceArray(minY..maxY).flatMap { row ->
            row.slice(minX..maxX).filter { it != this }
        }
    }
}

class SimulationManager {
    private val scheduledExecutor: ScheduledExecutorService = Executors.newScheduledThreadPool(2)
    private val animalExecutor = Executors.newCachedThreadPool()
    private val island = Array(Config.ISLAND_HEIGHT) { y ->
        Array(Config.ISLAND_WIDTH) { x -> Cell(x, y) }
    }

    init {
        island.forEach { row ->
            row.forEach { cell ->
                cell.island = island
            }
        }
        initializeAnimals()
    }

    private fun initializeAnimals() {
        Config.INITIAL_ANIMALS.forEach { (clazz, count) ->
            repeat(count) {
                val x = ThreadLocalRandom.current().nextInt(Config.ISLAND_WIDTH)
                val y = ThreadLocalRandom.current().nextInt(Config.ISLAND_HEIGHT)
                val cell = island[y][x]
                val animal = clazz.java.getConstructor(Cell::class.java).newInstance(cell)
                cell.addEntity(animal)
            }
        }
    }

    fun start() {
        scheduledExecutor.scheduleAtFixedRate({
            val tasks = island.flatMap { row ->
                row.flatMap { cell ->
                    cell.entities.filterIsInstance<Animal>().map { Runnable { it.run() } }
                }
            }
            tasks.forEach { task ->
                animalExecutor.submit(task)
            }
        }, 0, Config.SIMULATION_DELAY_MS, TimeUnit.MILLISECONDS)

        scheduledExecutor.scheduleAtFixedRate({
            island.forEach { row ->
                row.forEach { cell ->
                    if (ThreadLocalRandom.current().nextDouble() < 0.2) {
                        cell.addEntity(Plant(cell))
                    }
                    cell.entities.filterIsInstance<Plant>().forEach { it.grow() }
                }
            }
        }, 0, Config.SIMULATION_DELAY_MS, TimeUnit.MILLISECONDS)

        scheduledExecutor.scheduleAtFixedRate({
            val counts = mutableMapOf<String, Int>()
            island.forEach { row ->
                row.forEach { cell ->
                    cell.entities.forEach { entity ->
                        counts[entity.symbol] = counts.getOrDefault(entity.symbol, 0) + 1
                    }
                }
            }
            println("Population stats: ${counts.entries.joinToString { "${it.key}: ${it.value}" }}")
        }, 0, Config.SIMULATION_DELAY_MS, TimeUnit.MILLISECONDS)
    }
}

fun main() {
    SimulationManager().start()
}