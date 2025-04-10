package island

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class Simulation {
    private val island = Island()
    private val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(2)
    private var tick = 0

    fun start() {
        executor.scheduleAtFixedRate({
            println("\n--- Tick ${tick++} ---")
            island.simulate()
            printStats()
        }, 0, Config.SIMULATION_SPEED, TimeUnit.MILLISECONDS)
    }

    private fun printStats() {
        val stats = island.getStats()
        stats.entries.sortedBy { it.key }.forEach { (type, count) ->
            val emoji = Config.EMOJI_MAP[type] ?: ""
            println("$type $emoji: $count")
        }
    }
}