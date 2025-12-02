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
        log.info { "${super.year} - Day ${super.day}"}
        Utils.readInputResource("${super.year}/${super.day}.txt", sampleMode)?.map { parser(it) }?.let { inputItems ->
            log.info { "Part One: ${partOne(inputItems)}" }
            log.info { "Part Two: ${partTwo(inputItems)}" }
        }
    }

    companion object {
        val parser = { input: String ->
            input.split("x").map { dim -> dim.toInt() }.let { Triple(it[0], it[1], it[2]) }
        }
    }
}
