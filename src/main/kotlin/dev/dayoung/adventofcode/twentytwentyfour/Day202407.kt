package dev.dayoung.adventofcode.twentytwentyfour

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component

@Component
class Day202407 : PuzzleSolution(2024, 7, true) {
    private fun partOne(input: List<Pair<String, List<String>>>) : Long {
        return input.fold(0) { acc, entry -> acc + testNumberLine(entry.first.toLong(), 0, entry.second.map { it.toLong() }) }
    }

    private fun partTwo(input: List<Pair<String, List<String>>>) : Long {
        return input.fold(0) { acc, entry -> acc + testNumberLine(entry.first.toLong(), 0, entry.second.map { it.toLong() }, true) }
    }

    private fun testNumberLine(target: Long, runningTotal: Long, numbers: List<Long>, pt2Mode: Boolean = false): Long {
        if(runningTotal == target && numbers.isEmpty()) return runningTotal
        if(runningTotal > target || numbers.isEmpty()) return 0L
        var result = testNumberLine(target, runningTotal + numbers[0], numbers.drop(1), pt2Mode)
        if(result == target) return result
        result = testNumberLine(target, runningTotal * numbers[0], numbers.drop(1), pt2Mode)
        if(result == target) return result
        if(pt2Mode) {
            result = testNumberLine(target, "$runningTotal${numbers[0]}".toLong(), numbers.drop(1), pt2Mode)
            if (result == target) return result
        }
        return 0
    }

    val parser = { lines: List<String> ->
        lines.map {
            Pair(it.substringBefore(":"), it.substringAfter(":").trim().split(" ").map(String::trim))
        }.toList()
    }

    override fun solve(sampleMode: Boolean) {
        log.info { "2024 - Day 07" }
        val lines = Utils.readInputResource("2024/seven.txt", sampleMode)?.let { parser(it) }
        log.info { "Part 1: ${partOne(lines!!)}" }
        log.info { "Part 2: ${partTwo(lines!!)}" }
    }
}
