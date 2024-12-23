package dev.dayoung.adventofcode.twentytwentyfour

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import kotlin.math.pow

@Component
class Day202407 : PuzzleSolution(2024, 7, true) {
    private val log = KotlinLogging.logger {}

    private fun partOneRec(input: List<Pair<String, List<String>>>) : Long {
        var total = 0L
        for (entry in input) {
            val rec = testNumberLineRec(entry.first.toLong(), 0, entry.second.map { it.toLong() })
            total += rec
            val trad = testNumberLine(entry.first.toLong(), entry.second, findCombos(listOf("+", "*"), (entry.second.size - 1).toDouble()))
            log.debug {"entry: $entry, rec: $rec; trad: $trad" }
        }
        return total
    }

    fun partOne(input: List<Pair<String, List<String>>>) : Long {
        val operators = listOf("+", "*")
        var validTotal = 0L
        for(entry in input) {
            val lineTarget = entry.first.toLong()
            val lineNumbers = entry.second
            val operatorCombos = findCombos(operators, (lineNumbers.size - 1).toDouble())
            if(testNumberLine(lineTarget, lineNumbers.toMutableList(), operatorCombos)) {
                validTotal += lineTarget
            }
        }
        return validTotal
    }

    private fun testNumberLineRec(target: Long, runningTotal: Long, numbers: List<Long>): Long {
        if(runningTotal == target && numbers.isEmpty()) return runningTotal
        if(runningTotal > target || numbers.isEmpty()) return 0L
        var result = testNumberLineRec(target, runningTotal + numbers[0], numbers.drop(1))
        if(result == target) return result
        result = testNumberLineRec(target, runningTotal * numbers[0], numbers.drop(1))
        if(result == target) return result
        return 0
    }

    private fun testNumberLine(target: Long, numbers: List<String>, combos: List<List<String>>): Boolean {
        return combos.firstOrNull { combo ->
            val ns = numbers.toMutableList()
            var operString = ns.first()
            val sum = combo.fold(ns.removeFirst().toInt()) { acc, c ->
                val v = ns.removeFirst().toInt()
                operString += " $c $v"
                when(c) {
                    "+" -> acc + v
                    "*" -> acc * v
                    else -> {
                        log.info { "else" }
                        acc
                    }
                }
            }.toLong()
            sum == target
        }?.isNotEmpty() ?: false
    }

    val parser = { lines: List<String> ->
        lines.map {
            Pair(it.substringBefore(":"), it.substringAfter(":").trim().split(" ").map(String::trim))
        }.toList()
    }

    override fun solve(sampleMode: Boolean) {
        println("2024 - Day 07")
        log.info { "2024 - Day 07" }
        val lines = Utils.readInputResource(sampleMode, "2024/seven.txt")?.let { parser(it) }
        log.info { "Part 1: ${partOneRec(lines!!)}" }
    }

    private fun findCombos(operators:List<String>, places: Double): List<List<String>> {
        return (0 until 2.0.pow(places).toInt()).map { i ->
            (0 until places.toInt()).map { j ->
                operators[((i shr j) and 1)]
            }
        }
    }
}
