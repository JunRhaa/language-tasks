import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

fun main() {
    val inputFilePath = "input.txt"
    val outputFilePath = "output.txt"

    try {
        BufferedReader(FileReader(inputFilePath)).use { reader ->
            FileWriter(outputFilePath).use { writer ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    writer.write(line?.toUpperCase() + "\n")
                }
            }
        }
        println("Файл успешно обработан и записан.")
    } catch (e: IOException) {
        println("Ошибка при чтении или записи файла: ${e.message}")
    }
}
interface TextProcessor {
    fun process(text: String): String
}

class SimpleTextProcessor : TextProcessor {
    override fun process(text: String): String {
        return text
    }
}

class UpperCaseDecorator(private val processor: TextProcessor) : TextProcessor {
    override fun process(text: String): String {
        return processor.process(text).uppercase()
    }
}

class TrimDecorator(private val processor: TextProcessor) : TextProcessor {
    override fun process(text: String): String {
        return processor.process(text).trim()
    }
}

class ReplaceDecorator(private val processor: TextProcessor, private val replacement: Char = '_') : TextProcessor {
    override fun process(text: String): String {
        return processor.process(text).replace(' ', replacement)
    }
}

fun main() {
    val processor = ReplaceDecorator(
        UpperCaseDecorator(
            TrimDecorator(SimpleTextProcessor())
        )
    )

    val result = processor.process(" Hello world ")
    println(result) // Вывод: HELLO_WORLD
}
import java.io.*
import kotlin.system.measureTimeMillis

fun readFileWithIO(filePath: String): String {
    val buffer = StringBuilder()
    BufferedReader(FileReader(filePath)).use { reader ->
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            buffer.append(line).append("\n")
        }
    }
    return buffer.toString()
}

fun writeFileWithIO(filePath: String, content: String) {
    BufferedWriter(FileWriter(filePath)).use { writer ->
        writer.write(content)
    }
}

fun main() {
    val inputFilePath = "large_input.txt"
    val ioOutputFilePath = "io_output.txt"

    val ioTime = measureTimeMillis {
        val content = readFileWithIO(inputFilePath)
        writeFileWithIO(ioOutputFilePath, content)
    }

    println("Время выполнения с использованием IO: $ioTime ms")
}
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.charset.StandardCharsets
import kotlin.system.measureTimeMillis

fun readFileWithNIO(filePath: String): String {
    return String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8)
}

fun writeFileWithNIO(filePath: String, content: String) {
    Files.write(Paths.get(filePath), content.toByteArray(StandardCharsets.UTF_8))
}

fun main() {
    val inputFilePath = "large_input.txt"
    val nioOutputFilePath = "nio_output.txt"

    val nioTime = measureTimeMillis {
        val content = readFileWithNIO(inputFilePath)
        writeFileWithNIO(nioOutputFilePath, content)
    }

    println("Время выполнения с использованием NIO: $nioTime ms")
}
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel

fun copyFileUsingNIO(sourcePath: String, destPath: String) {
    FileInputStream(sourcePath).use { source ->
        FileOutputStream(destPath).use { dest ->
            val sourceChannel: FileChannel = source.channel
            val destChannel: FileChannel = dest.channel
            sourceChannel.transferTo(0, sourceChannel.size(), destChannel)
        }
    }
}

fun main() {
    val sourceFilePath = "source_file.txt"
    val destFilePath = "destination_file.txt"

    try {
        copyFileUsingNIO(sourceFilePath, destFilePath)
        println("Файл успешно скопирован.")
    } catch (e: Exception) {
        println("Ошибка при копировании файла: ${e.message}")
    }
}
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.concurrent.Future

fun readAsyncFile(filePath: String) {
    val path: Path = Paths.get(filePath)
    AsynchronousFileChannel.open(path, StandardOpenOption.READ).use { channel ->
        val buffer = ByteBuffer.allocate(1024)
        val future: Future<Int> = channel.read(buffer, 0L)

        while (true) {
            try {
                val bytesRead = future.get()
                if (bytesRead == -1) break
                buffer.flip()
                val data = ByteArray(bytesRead)
                buffer[data]
                println(String(data))
                buffer.clear()
            } catch (e: Exception) {
                println("Ошибка при асинхронном чтении файла: ${e.message}")
                break
            }
        }
    }
}

fun main() {
    val filePath = "async_input.txt"
    readAsyncFile(filePath)
}
