package dev.dayoung.adventofcode.twentyfifteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component

@Component
class Day01: PuzzleSolution(2015, 1) {
    private fun partOne(input: List<Int>): Int = input.last()
    private fun partTwo(input: List<Int>): Int = input.indexOfFirst { it < 0 }

    override fun solve(sampleMode: Boolean) {
        log.info { "${super.year} Day ${super.day}" }
        Utils.readInputResource("${super.year}/${super.day}.txt", sampleMode)?.let { parser(it.first()) }?.let { input ->
            log.info { "Part One: ${partOne(input)}" }
            log.info { "Part Two: ${partTwo(input)}" }
        }
    }

    companion object {
        val parser = { input: String ->
            input.fold(listOf(0)) { acc, v ->
                acc + when(v) {
                    '(' -> acc.last() + 1
                    ')' -> acc.last() - 1
                    else -> acc.last()
                }
            }
        }
    }
}
