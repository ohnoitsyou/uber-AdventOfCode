package dev.dayoung.adventofcode.twentyfifteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils

class Day07: PuzzleSolution(2015, 7) {

    data class Instruction(val input: String, val output: String)
    override fun solve(sampleMode: Boolean) {
        println("2015 Day 07")
        Utils.readInputResource(sampleMode, "2015/seven.txt")?.let { circuit ->
            circuit.toCircuitInstructions()
        }
    }
}

fun List<String>.toCircuitInstructions() : List<Day07.Instruction> {
    val instructionRegex = Regex("""(.*) -> (\w+)""")
    return this.map {
        val (input, output) = instructionRegex.find(it)?.destructured!!
        Day07.Instruction(input, output)
    }
}