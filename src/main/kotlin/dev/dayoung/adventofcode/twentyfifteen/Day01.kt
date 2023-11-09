package dev.dayoung.adventofcode.twentyfifteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component

@Component
class Day01: PuzzleSolution(2015, 1) {

    private fun processInput(input: List<String>): List<Int>{
        var floor = 0
        return input.first().map {
            when(it) {
                '(' -> floor + 1
                ')' -> floor - 1
                else -> floor
            }.also { floor = it }
        }
    }

    private fun partOne(input: List<Int>) {
        println("Final floor: ${input.last()}")
    }

    private fun partTwo(input: List<Int>) {
        // index should be 1 based
        val basement = input.indexOfFirst { it < 0 } + 1
        println("Basement index: $basement")

    }
    override fun solve(sampleMode: Boolean) {
        println("2015 Day 01")
        val inputString = Utils.readInputResource(sampleMode, "2015/one.txt")
        val processedInput = inputString?.let { processInput(it) }
        if (processedInput != null) {
            partOne(processedInput)
            partTwo(processedInput)
        }
    }
}