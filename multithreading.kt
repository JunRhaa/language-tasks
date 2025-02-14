import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.CountDownLatch
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore
import java.util.concurrent.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class Counter {
    private var count = 0
    private val lock = ReentrantLock()

    fun increment() {
        lock.lock()
        try {
            count++
        } finally {
            lock.unlock()
        }
    }

    fun getCount(): Int {
        return count
    }
}

fun main() {
    val counter = Counter()
    val threads = mutableListOf<Thread>()

    repeat(5) { threadIndex ->
        threads.add(Thread {
            repeat(1000) {
                counter.increment()
            }
        })
    }

    threads.forEach { it.start() }
    threads.forEach { it.join() }

    println("Final count: ${counter.getCount()}")
}
fun main() {
    val list = CopyOnWriteArrayList<Int>()
    val threads = mutableListOf<Thread>()

    repeat(10) { threadIndex ->
        threads.add(Thread {
            for (i in 1..100) {
                list.add(i)
            }
        })
    }

    threads.forEach { it.start() }
    threads.forEach { it.join() }

    println("List size: ${list.size}")
}
fun main() {
    val executor = Executors.newFixedThreadPool(4)

    repeat(20) { taskNumber ->
        executor.submit {
            println("Task $taskNumber is running on thread ${Thread.currentThread().name}")
        }
    }

    executor.shutdown()
    executor.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS)
}
class BankAccount(var balance: Int) {
    private val lock = ReentrantLock()

    fun transfer(toAccount: BankAccount, amount: Int) {
        val fromLock = this.lock
        val toLock = toAccount.lock

        val firstLock = if (System.identityHashCode(fromLock) < System.identityHashCode(toLock)) fromLock else toLock
        val secondLock = if (firstLock == fromLock) toLock else fromLock

        firstLock.lock()
        try {
            secondLock.lock()
            try {
                if (this.balance >= amount) {
                    this.balance -= amount
                    toAccount.balance += amount
                }
            } finally {
                secondLock.unlock()
            }
        } finally {
            firstLock.unlock()
        }
    }
}

fun main() {
    val account1 = BankAccount(1000)
    val account2 = BankAccount(1000)

    val threads = mutableListOf<Thread>()

    repeat(10) {
        threads.add(Thread {
            account1.transfer(account2, 100)
        })
    }

    threads.forEach { it.start() }
    threads.forEach { it.join() }

    println("Account 1 balance: ${account1.balance}")
    println("Account 2 balance: ${account2.balance}")
}
fun main() {
    val semaphore = Semaphore(2)

    repeat(10) { index ->
        Thread {
            semaphore.acquire()
            try {
                println("Thread $index is using the resource")
                Thread.sleep((Math.random() * 1000).toLong())
            } finally {
                semaphore.release()
                println("Thread $index has released the resource")
            }
        }.start()
    }
}
fun main() {
    val executor = Executors.newFixedThreadPool(10)
    val futures = mutableListOf<Future<Long>>()

    repeat(10) { index ->
        futures.add(executor.submit(Callable {
            factorial(index + 1)
        }))
    }

    futures.forEachIndexed { index, future ->
        println("Factorial of ${index + 1} is ${future.get()}")
    }

    executor.shutdown()
}

fun factorial(n: Long): Long {
    return if (n <= 1) n else n * factorial(n - 1)
}
fun main() {
    val queue: BlockingQueue<String> = LinkedBlockingQueue()

    val producer = Thread {
        for (i in 1..10) {
            val item = "Item $i"
            queue.put(item)
            println("Produced $item")
            Thread.sleep((Math.random() * 1000).toLong())
        }
        queue.put("END") // Signal consumer to stop
    }

    val consumer = Thread {
        while (true) {
            val item = queue.take()
            if (item == "END") break
            println("Consumed $item")
            Thread.sleep((Math.random() * 1000).toLong())
        }
    }

    producer.start()
    consumer.start()

    producer.join()
    consumer.join()
}
fun main() {
    val array = intArrayOf(34, 7, 23, 32, 5, 62, 32, 2, 78, 1)
    val numberOfThreads = 4
    val chunkSize = (array.size + numberOfThreads - 1) / numberOfThreads
    val latch = CountDownLatch(numberOfThreads)

    repeat(numberOfThreads) { threadIndex ->
        Thread {
            val start = threadIndex * chunkSize
            val end = minOf(start + chunkSize, array.size)
            array.sliceArray(start until end).sorted().copyInto(array, start)
            latch.countDown()
        }.start()
    }

    latch.await()

    println("Sorted array: ${array.joinToString(", ")}")
}
class Philosopher(val name: String, val leftFork: ReentrantLock, val rightFork: ReentrantLock) : Runnable {
    override fun run() {
        repeat(5) {
            think()
            pickUpLeftFork()
            pickUpRightFork()
            eat()
            putDownLeftFork()
            putDownRightFork()
        }
    }

    private fun think() {
        println("$name is thinking")
        Thread.sleep((Math.random() * 1000).toLong())
    }

    private fun eat() {
        println("$name is eating")
        Thread.sleep((Math.random() * 1000).toLong())
    }

    private fun pickUpLeftFork() {
        leftFork.lock()
        println("$name picked up left fork")
    }

    private fun pickUpRightFork() {
        rightFork.lock()
        println("$name picked up right fork")
    }

    private fun putDownLeftFork() {
        leftFork.unlock()
        println("$name put down left fork")
    }

    private fun putDownRightFork() {
        rightFork.unlock()
        println("$name put down right fork")
    }
}

fun main() {
    val forks = Array(5) { ReentrantLock() }
    val philosophers = Array(5) { i ->
        Philosopher("Philosopher ${i + 1}", forks[i], forks[(i + 1) % 5])
    }

    philosophers.forEach { Thread(it).start() }
}
fun main() {
    val matrixA = arrayOf(
        intArrayOf(1, 2, 3),
        intArrayOf(4, 5, 6),
        intArrayOf(7, 8, 9)
    )

    val matrixB = arrayOf(
        intArrayOf(9, 8, 7),
        intArrayOf(6, 5, 4),
        intArrayOf(3, 2, 1)
    )

    val result = Array(matrixA.size) { IntArray(matrixB[0].size) }
    val executor: ExecutorService = Executors.newFixedThreadPool(matrixA.size)

    repeat(matrixA.size) { row ->
        executor.execute {
            for (col in matrixB[0].indices) {
                for (inner in matrixB.indices) {
                    result[row][col] += matrixA[row][inner] * matrixB[inner][col]
                }
            }
        }
    }

    executor.shutdown()
    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)

    println("Result matrix:")
    result.forEach { row ->
        println(row.joinToString(" "))
    }
}
fun main() {
    val running = AtomicBoolean(true)

    val timerThread = Thread {
        var seconds = 0
        while (running.get()) {
            println("Time elapsed: $seconds seconds")
            Thread.sleep(1000)
            seconds++
        }
    }

    val stopperThread = Thread {
        Thread.sleep(10000)
        running.set(false)
    }

    timerThread.start()
    stopperThread.start()

    timerThread.join()
    stopperThread.join()
}