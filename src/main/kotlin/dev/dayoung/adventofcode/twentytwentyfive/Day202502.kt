package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.toLongRanges
import org.springframework.stereotype.Component

@Component
class Day202502: PuzzleSolution(2025, 2, true) {

    fun partOne(input: List<LongRange>): Long {
        return input.flatMap { range ->
                range.filter { it.toString().length % 2 == 0 }
            }.map { it.toString() }
            .filter { id ->
                id.substring(0, id.length / 2).let { seq ->
                    id == "$seq$seq"
                }
            }.sumOf { it.toLong() }
    }

    fun partTwo(input: List<LongRange>): Long {
        return input.flatMap { range ->
            range.filter {
                val value = it.toString()
                (1..(value.length / 2)).any { idx ->
                    Regex("""^(${value.take(idx)})+$""").containsMatchIn(value.drop(idx))
                }
            }
        }.sum()
    }

    override fun solve(sampleMode: Boolean) {
        val input =
            Utils.readInputResource("2025/02.txt", sampleMode)?.first()?.split(",")?.toLongRanges() ?: emptyList()
        log.info { "Invalid ID sum: ${partOne(input)}" }
        log.info { "Sum: ${partTwo(input)}" }
    }
}
