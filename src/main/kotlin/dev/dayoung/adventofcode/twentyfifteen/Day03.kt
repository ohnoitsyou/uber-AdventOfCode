package dev.dayoung.adventofcode.twentyfifteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.Vec2i
import dev.dayoung.adventofcode.Vec2i.Companion.ORIGIN
import org.springframework.stereotype.Component

@Component
class Day03 : PuzzleSolution(2015, 3){

    val cardinalMap = mapOf(
        '>' to Vec2i.RIGHT,
        '<' to Vec2i.LEFT,
        '^' to Vec2i.UP,
        'v' to Vec2i.DOWN
    )

    fun partOne(input: String): Int {
        return input.toList().fold(listOf(ORIGIN)) { acc, direction: Char ->
            acc + (acc.last() + cardinalMap[direction]!!)
        }.toSet().size
    }

    fun partTwo(input: String): Int {
        return input.toList().chunked(2).fold(listOf(ORIGIN to ORIGIN)) { acc, (santa, robot) ->
            acc + (acc.last().first + cardinalMap[santa]!! to acc.last().second + cardinalMap[robot]!!)
        }.unzip().let {
            it.first.union(it.second)
        }.size
    }

    override fun solve(sampleMode: Boolean) {
        log.info { "${super.year} - Day ${super.day}"}
        Utils.readInputResource("${super.year}/${super.day}.txt", sampleMode)?.let { input ->
            log.info { "Part One: ${partOne(input.first())}" }
            log.info { "Part Two: ${partTwo(input.first())}" }
        }
    }
}
