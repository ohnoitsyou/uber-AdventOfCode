package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component

@Component
class Day202503: PuzzleSolution(2025, 3) {
    fun partOne(input: List<String>): Int {
        return input.sumOf { bank ->
            (0..bank.lastIndex).flatMap { i ->
                (i + 1 .. bank.lastIndex).map { j ->
                    "${bank[i]}${bank[j]}".toInt()
                }
            }.toSet().max()
        }
    }

    fun partTwo(input: List<String>): Long {
        val banksOn = input.map { bank ->
            bank.map { it.digitToInt() }.let { str: List<Int> ->
                val on = mutableListOf<Int>()
                val candidates = str.toMutableList()
                while (on.size < 12) {
                    val cur = candidates.dropLast(11 - on.size)
                    val max = cur.max()
                    on.add(max)
                    val maxIdx = cur.indexOf(max)
                    candidates.subList(0, maxIdx + 1).clear()
                }
                on
            }.joinToString("").toLong()
        }
        return banksOn.sum()
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2025/03.txt", sampleMode)?.let { input ->
            log.info { "Part One: ${partOne(input)}" }
            log.info { "Part Two: ${partTwo(input)}" }
        }
    }
}
