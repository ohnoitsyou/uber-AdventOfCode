package dev.dayoung.adventofcode

import com.github.ajalt.clikt.core.CliktCommand
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.time.measureTime

@SpringBootApplication
class UAoCApplication(val solutions: List<PuzzleSolution>): CliktCommand(), CommandLineRunner {

    // CliKt's run method
    override fun run() = doRun()
    // Spring's run method
    override fun run(vararg args: String?) = doRun()

    fun doRun() {
        val sortedSolutions = solutions.sortedWith(compareBy<PuzzleSolution> { it.year }.thenBy { it.day })
        println("Number of solutions found: ${sortedSolutions.size}")

        val skipList = listOf(Pair(2015, 4))

        val sampleMode = false
        val oneShot = true
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
                        it.main()
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
    val log = KotlinLogging.logger { }
    abstract fun solve(sampleMode: Boolean = false)

    open fun main() {
        this.solve(false)
    }
}

fun main(args: Array<String>) {
    runApplication<UAoCApplication>(*args)
}
