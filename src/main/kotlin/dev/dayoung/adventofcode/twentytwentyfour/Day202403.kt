package dev.dayoung.adventofcode.twentytwentyfour

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component

@Component
class Day202403: PuzzleSolution(2024, 3) {
    val mulInst = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
    val doInst = Regex("""do\(\)""")
    val dontInst = Regex("""don't\(\)""")

    fun partOne(instructions: List<String>): Long {
        return mulInst.findAll(instructions.joinToString("")).sumOf {
            val (f, s) = it.destructured
            f.toLong() * s.toLong()
        }
    }

    fun partTwo(instructions: List<String>): Long {
        val joined = instructions.joinToString("")
        val sparseInstructions = mutableListOf<Pair<Int, Pair<Long, Long>>>()
        mulInst.findAll(joined).mapTo(sparseInstructions) {
            val (f, s) = it.destructured
            it.range.first to (f.toLong() to s.toLong())
        }
        doInst.findAll(joined).mapTo(sparseInstructions) {
            it.range.first to (0L to 0L)
        }
        dontInst.findAll(joined).mapTo(sparseInstructions) {
            it.range.first to (-1L to -1L)
        }
        var enabled = true
        return sparseInstructions.sortedBy { it.first }.sumOf { (_, pair) ->
            val (f, s) = pair
            if (f == -1L && s == -1L) {
                enabled = false
                0
            } else if( f == 0L && s == 0L) {
                enabled = true
                0
            } else {
                if(enabled) f * s else 0
            }
        }
    }

    override fun solve(sampleMode: Boolean) {
        println("2024 - Day 03")
        Utils.readInputResource(sampleMode, "2024/three.txt")?.let { instructions ->
            println("Part One: ${partOne(instructions)}")
            println("Part Two: ${partTwo(instructions)}")
        }
    }
}
