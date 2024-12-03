package dev.dayoung.adventofcode.twentytwentythree

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component
import kotlin.time.Duration.Companion.seconds

@Component
class Day202301 : PuzzleSolution(2023, 1) {

    private final val digitRegex = Regex("[0-9]")
    private final val words = listOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)
    private final val lookaheadRegex = """(?=(${words.joinToString("|") { it.first } + "|[0-9]"}))""".toRegex()
    fun partOne(lines: List<String>): Int = lines.sumOf { line ->
        digitRegex.findAll(line).let { matches ->
            "${matches.first().value}${matches.last().value}".toInt()
        }
    }

    fun partTwo(lines: List<String>): Int = lines.sumOf { line ->
        lookaheadRegex.findAll(line).let { matches ->
            listOf(matches.first().groupValues[1], matches.last().groupValues[1]).map { value ->
                words.first { word -> word.first == value || "${word.second}" == value }.second
            }
        }.joinToString("").toInt()
    }
    override fun solve(sampleMode: Boolean) {
        println("\t2023 Day 01")
        Utils.readInputResource(sampleMode, "2023/one.txt")?.let {
            println("\tPart One: ${partOne(it)}")
            println("\tPart Two: ${partTwo(it)}")
        }
    }
}
