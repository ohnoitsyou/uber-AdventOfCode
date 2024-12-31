package dev.dayoung.adventofcode.twentynineteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.structures.IntcodeComputer

fun main() {
    Day201902().main()
}

class Day201902: PuzzleSolution(2019, 2, true) {
    fun partOne(input: List<String>): Int {
        val instructions = input.toMutableList()
        val computer = IntcodeComputer(instructions)
        computer.loadWorkingMemory()
        computer.setup("12", "2")
        return computer.run(0)
    }

    fun partTwo(input: List<String>): Int {
        val computer = IntcodeComputer(input)
        val noun = (0..99).iterator()
        while(noun.hasNext()) {
            val n = noun.next()
            val verb = (0..99).iterator()
            while (verb.hasNext()) {
                val v = verb.next()
                computer.loadWorkingMemory()
                computer.setup(n.toString(), v.toString())
                val output = computer.run(0)
                if (output == 19690720) {
                    return 100 * n + v
                }
            }
        }
        return -1
    }

    val parser = { input: String -> input.split(",".toRegex()) }

    override fun solve(sampleMode: Boolean) {
        log.info { "2019 - Day 2" }
        Utils.readInputResource(sampleMode, "2019/2.txt")?.let { parser(it.first()) }?.let { input ->
            log.info { "Part one: ${partOne(input)}" }
            log.info { "Part two: ${partTwo(input)}" }
        }
    }
}
