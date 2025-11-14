package dev.dayoung.adventofcode.twentytwentyfour

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component
import kotlin.math.abs
import kotlin.math.sign

@Component
class Day202402: PuzzleSolution(2024, 2) {
    data class Report(val values: List<Int>)

    enum class Valid {
        TRUE, MAYBE, FALSE
    }

    // Borrowed from: https://github.com/Verulean/Advent-of-Code-2024/blob/main/src/main/kotlin/days/Solution02.kt
    fun expectedSign(diffs: List<Int>): Int {
        val counts = diffs.groupingBy { it.sign }.eachCount()
        return if(counts.getOrDefault(1, 0) > counts.getOrDefault(-1, 0)) 1 else -1
    }

    fun computeSafety(report: Report): Valid {
        val diffs = report.values.zipWithNext { a, b -> b - a }
        fun safeXfer(diff: Int) = abs(diff) in 1..3 && diff.sign == expectedSign(diffs)
        return if(diffs.all { safeXfer(it) }) {
            Valid.TRUE
        } else {
            Valid.FALSE
//            val candidates = report.values.mapIndexedNotNull { index, i ->  report.values.subList(inu)}
        }
    }

    fun partOne(reports: List<Report>): Int {
        return reports.count { computeSafety(it) == Valid.TRUE }
    }

    fun partTwo(reports: List<Report>): Int {
        val groupedReports = reports.groupBy { report -> computeSafety(report) }
        val rerunReports = groupedReports[Valid.FALSE]?.map { report ->
            val removalCandidates = report.values.mapIndexedNotNull { index, i ->  }
        }

        return 0
    }

    override fun solve(sampleMode: Boolean) {
        println("2024 - Day 02")
        Utils.readInputResource(sampleMode, "input/2024/two.txt")?.let {
            val reports = it.map { Report(it.split(" ".toRegex()).map { it.toInt() }) }
            println("Part 01: ${partOne(reports)}")
            println("Part 02: ${partTwo(reports)}")
        }
    }
}
