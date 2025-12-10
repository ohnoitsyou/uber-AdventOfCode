package dev.dayoung.adventofcode

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.options.pair
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.time.measureTime

@SpringBootApplication
class UAoCApplication(val solutions: List<PuzzleSolution>): CliktCommand(), CommandLineRunner {
    val sampleMode by option("--sample", help = "What mode should be used").flag(default = false)
    val oneshot by option("--oneshot", "--single", help = "Run one-shot mode").int().pair()

    companion object {
        val log = KotlinLogging.logger {  }
    }

    // CliKt's run method
    override fun run() = doRun()
    // Spring's run method
    override fun run(vararg args: String?) = doRun()

    fun doRun() {
        """
            |   _       _                 _               ___  __             ___          _
            |  /_\   __| |_   _____ _ __ | |_            /___\/ _|           / __\___   __| | ___
            | //_\\ / _` \ \ / / _ \ '_ \| __|  _____   //  // |_   _____   / /  / _ \ / _` |/ _ \
            |/  _  \ (_| |\ V /  __/ | | | |_  |_____| / \_//|  _| |_____| / /__| (_) | (_| |  __/
            |\_/ \_/\__,_| \_/ \___|_| |_|\__|         \___/ |_|           \____/\___/ \__,_|\___|
        """.trimMargin()
        val sortedSolutions = solutions.sortedWith(compareBy<PuzzleSolution> { it.year }.thenBy { it.day })
        println("Number of solutions found: ${sortedSolutions.size}")

        val skipList = listOf(Pair(2015, 4))

        val sampleMode = this.sampleMode
        val oneShot = this.oneshot != null
        val single = Pair(2024, 1)

        val targetSolutions = if (!oneShot) {
            sortedSolutions
                // Filter out expensive ones
                .filterNot { Pair(it.year, it.day) in skipList }
                .filter { if (sampleMode) it.hasSample else true }
        } else {
            sortedSolutions
                .filter { it.year == single.first && it.day == single.second }
            }

        runBlocking {
            targetSolutions.forEach {
                launch {
                    measureTime {
                        it.main(sampleMode)
//                        it.solve(sampleMode)
                    }.inWholeMicroseconds.let {
                        println("$it μ sec")
                    }
                }
            }
        }
    }

}

abstract class PuzzleSolution(val year: Int, val day: Int, val hasSample: Boolean = false) {
    open val log: KLogger by logger()
    abstract fun solve(sampleMode: Boolean = false)

    open fun main(sampleMode: Boolean = false) {
        this.solve(sampleMode)
    }

    fun <T> T.logit(message: String = ""): T {
        log.info { if(message.isEmpty()) this else "$message: $this" }
        return this
    }
}

fun <R: Any> R.logger(): Lazy<KLogger> {
    return lazy { KotlinLogging.logger(getClassName(this.javaClass))}
}

fun <T : Any> getClassName(clazz: Class<T>): String {
    return clazz.name.removeSuffix("\$Companion")
}

fun main(args: Array<String>) {
    runApplication<UAoCApplication>(*args)
}
