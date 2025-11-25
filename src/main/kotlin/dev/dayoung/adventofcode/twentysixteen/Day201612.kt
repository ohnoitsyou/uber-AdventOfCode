package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.structures.AssembunnyComputer
import org.springframework.stereotype.Component

@Component
class Day201612: PuzzleSolution(2016, 12) {
    fun partOne(input: List<String>): Int {
        val computer = AssembunnyComputer(input)
        computer.run()
        return computer["a"]!!.value
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource(sampleMode, "2016/twelve.txt")?.let { input ->
            println("p1: Register A value: ${partOne(input)}")
            println("p2: Register A value: ${partOne(input.toMutableList().apply { addFirst("cpy 1 c") })}")
        }
    }
}
