package dev.dayoung.adventofcode.twentyfifteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component

@Component
class Day05: PuzzleSolution(2015, 5) {
    fun partTwo(inputs: List<String>): Int {
        val surroundRx = Regex("""(?<c>.).(\k<c>)""")
        val pairRx = Regex("""(?<c1>.)(?<c2>.).*\k<c1>\k<c2>""")
        return inputs.count {
            pairRx.containsMatchIn(it) && surroundRx.containsMatchIn(it)
        }
    }
    fun partOne(inputs: List<String>): Int {
        val doubleRegex = Regex("""(?<c>.)(\k<c>)""")
        val vowelsRegex = Regex("""[aeiou]""")
        val bannedRegex = Regex("""(ab|cd|pq|xy)""")
        return inputs.count {
            vowelsRegex.findAll(it).count() >= 3 &&
            doubleRegex.containsMatchIn(it) &&
            !bannedRegex.containsMatchIn(it)
        }
    }

    override fun solve(sampleMode: Boolean) {
        println("2015 Day 05")
        Utils.readInputResource(sampleMode, "2015/five.txt")?.let {
            println("Part One: ${partOne(it)}")
            println("Part Two: ${partTwo(it)}")
        }
    }
}