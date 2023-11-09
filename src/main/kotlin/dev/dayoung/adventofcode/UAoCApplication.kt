package dev.dayoung.adventofcode

import com.github.ajalt.clikt.core.CliktCommand
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UAoCApplication(val solutions: List<PuzzleSolution>): CliktCommand(), CommandLineRunner{

    // CliKt's run method
    override fun run() = doRun()
    // Spring's run method
    override fun run(vararg args: String?) = doRun()

    fun doRun() {
        val sortedSolutions = solutions.sortedBy { it.year * it.day }
        println("Number of solutions found: ${sortedSolutions.size}")

        runBlocking {
            sortedSolutions
                // Filter out expensive ones
                .filter { it.year == 2015 && it.day != 4 }
                .forEach {
                launch {
                    it.solve(false)
                }
            }
        }
    }

}

abstract class PuzzleSolution(val year: Int, val day: Int) {
    abstract fun solve(sampleMode: Boolean)
}

fun main(args: Array<String>) {
    runApplication<UAoCApplication>(*args)
}
