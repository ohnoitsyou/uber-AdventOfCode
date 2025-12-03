package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils

class Day202503: PuzzleSolution(2025, 3) {
    fun maxWithTail(bank: List<Int>): Pair<Int, List<Int>> {
        return bank.max().let { it to bank.subList(bank.indexOf(it) + 1, bank.lastIndex) }
    }

    fun partOne(input: List<String>): Int {
        return input.sumOf { bank ->
            (0..bank.lastIndex).flatMap { i ->
                (i + 1 .. bank.lastIndex).map { j ->
                    "${bank[i]}${bank[j]}".toInt()
                }
            }.toSet().max()
        }
    }

    fun nextHighestBeforeLower(bank: List<Int>): Pair<Int, List<Int>> {
        val windows = bank.windowed(2)
        return windows.indexOfFirst { it[0] >= it[1] || it[0] == it[1] }.let { if(bank.lastIndex > it + 1) bank[it] to bank.drop(it + 1) else bank[it] * 10 + bank[it + 1] to emptyList() }
    }

    fun partTwo(input: List<String>): Long {
        input[2].let { str ->
            var digitList = str.map { it.digitToInt() }
            buildString {
                repeat(12) {
                    val (p1, b1) = nextHighestBeforeLower(digitList)
                    append(p1)
                    digitList = b1
                }
            }.also { log.info { it } }
//            val (p2, b2) = nextHighestBeforeLower(b1)
//            log.info { "p1: $p1, b1: $b1, p2: $p2, b2: $b2" }
        }
//        val banks: List<String> = input.map { bank ->
//            var b: List<Int> = bank.map { it.digitToInt() }
//            ""
//        }
//        log.info { "Banks: $banks" }
        return 0
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2025/03.txt", sampleMode)?.let { input ->
//            log.info { "Part One: ${partOne(input)}" }
            log.info { "Part Two: ${partTwo(input)}" }
        }
    }
}

fun main() {
    Day202503().solve(true)
}
