package dev.dayoung.adventofcode.twentyfifteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.apache.commons.text.StringEscapeUtils
import org.springframework.stereotype.Component

typealias D8Output = Triple<String, Int, Int>

@Component
class Day08: PuzzleSolution(2015, 8, true) {

    fun partOne(lines: List<String>): Int {
        val hexRegex = Regex("""\\x[0-9a-fA-F]{2}""")
        val processedLines = lines.map { item ->
            val replacedHex = StringEscapeUtils.unescapeJava(item.drop(1).dropLast(1).replace(hexRegex) { " " })
            Triple(item, item.length, replacedHex.length)
        }
        return processedLines.sumOf { it.second } - processedLines.sumOf { it.third }
    }
    fun partTwo(lines: List<String>): Int {
        val processedLines = lines.map { item ->
            Triple(item, item.length, StringEscapeUtils.escapeJson(item).length + 2)
        }
        return processedLines.sumOf { it.third } - processedLines.sumOf { it.second }
    }
    override fun solve(sampleMode: Boolean) {
        println("2015 Day 08")
        Utils.readInputResource("2015/eight.txt", sampleMode)?.let { lines ->
            println("Part One: ${partOne(lines)}")
            println("Part Two: ${partTwo(lines)}")
        }
    }
}
