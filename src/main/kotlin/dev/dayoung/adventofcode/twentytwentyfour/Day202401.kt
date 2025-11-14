package dev.dayoung.adventofcode.twentytwentyfour

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component
import kotlin.math.abs

@Component
class Day202401 : PuzzleSolution(2024, 1, true) {

    fun partOne(lines: Pair<List<Int>, List<Int>>): Int {
        return (lines.first zip lines.second).sumOf { (first, second) -> abs(first - second) }
    }

    fun partTwo(lines: Pair<List<Int>,List<Int>>): Int {
        return lines.first.sumOf { source ->
            source * lines.second.count { source == it }
        }
    }

    override fun solve(sampleMode: Boolean) {
        println("2024 - Day 01")
        Utils.readInputResource(sampleMode, "input/2024/one.txt")?.let {
            val numberLists: Pair<List<Int>, List<Int>> = it.map { line ->
                line.split(" +".toRegex())
                    .map { word -> word.toInt(10) }
                    .run { first() to last() }
            }
                .unzip()
                .run {
                    first.sorted() to second.sorted()
                }
            println("part 1: ${partOne(numberLists)}")
            println("part 2: ${partTwo(numberLists)}")
        }
    }
}
