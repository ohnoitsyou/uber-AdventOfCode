package dev.dayoung.adventofcode.twentyfifteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component

typealias lwh = Triple<Int, Int, Int>

@Component
class Day02: PuzzleSolution(2015, 2) {

    fun partOne(entries: List<lwh>): Int =
        entries.fold(0) { sum, entry ->
            val sides = listOf(entry.first * entry.second, entry.second * entry.third, entry.first * entry.third)
            sum + sides.sumOf { it * 2 } + sides.min()
        }

    fun partTwo(entries: List<lwh>): Int =
        entries.map { it.toList().sorted() }.sumOf { entry ->
            entry.reduce { sum, element -> sum * element } + entry.dropLast(1).sumOf { it * 2 }
        }


    override fun solve(sampleMode: Boolean) {
        println("2015 Day 02")
        Utils.readInputResource(sampleMode, "2015/two.txt")?.map(::lineToDimensions)?.let { inputItems ->
            println("Part One: ${partOne(inputItems)}")
            println("Part Two: ${partTwo(inputItems)}")
        }
    }

    companion object {
        fun lineToDimensions(line: String): lwh = line.split("x").map {
            it.toInt()
        }.let {
            Triple(it[0], it[1], it[2])
        }
    }
}