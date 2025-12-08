package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.structures.Grid

class Day202506 : PuzzleSolution(2025, 6) {
    val lineRx = Regex("""([0-9]+)+?""")
    val operRx = Regex("""([*+])""")


    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2025/06.txt", sampleMode)?.let { lines ->
//            partOne(lines)
            partTwoGrid(lines).logit("Part Two")
        }
    }
    fun partOne(lines: List<String>): Long {
        val gridX = lineRx.findAll(lines.first()).map { it.value.toLong() }.count()
        val points = lines.dropLast(1).flatMap { l -> lineRx.findAll(l).map { it.value.toLong() }.toList() }
        val operations = operRx.findAll(lines.takeLast(1).first()).map { it.value }.toList()
        val eqns = points.withIndex().groupBy { idxv -> idxv.index % gridX }
        return eqns.map { (idx, values) ->
            if(operations[idx] == "+") {
                values.sumOf { it.value }
            } else {
                values.map { it.value}.reduce { acc: Long, i: Long ->  acc * i }
            }
        }.sum().logit("Totals")
    }
    fun partTwo(input: List<String>): Long {
        val gridX = input.first().length.logit("gridX")
        val points = input.dropLast(1).joinToString("").toList().logit("points")
        log.info { points.chunked(gridX) }

        val operations = operRx.findAll(input.takeLast(1).first()).map { it.value }.toList()

        val gpwi = points.withIndex().groupBy { it.index % gridX }
        val gpv = gpwi.map { (_, iv) ->
            iv.map { it.value }.filter { it != ' ' }.joinToString("")
        }
        val fgpv = gpv.filter { it.isNotEmpty() }
        val fpc = fgpv.chunked(input.size - 1).map { chunk -> chunk.map { number -> number.toLong() }}
        log.info { "Grouped points: $fpc" }
        log.info { "operations: $operations" }
        val sums = fpc.mapIndexed { idx, l ->
            if(operations[idx] == "+") {
                l.sumOf { it }
            } else {
                l.reduce { acc: Long, i: Long ->  acc * i }
            }
        }.sum()
        log.info { "Sums: $sums" }


        // build out the numbers, rtl / ttb
        // When parsing, take each line and split on each character,
        // when doing equations, look for columns that are all empty-string
        return 0L
    }

    fun partTwoGrid(input: List<String>): Long {
        val gridX = input.first().length.logit("gridX")
        val points = input.dropLast(1).joinToString("").toList().logit("points")
        val operations = Regex("""([*+]) +""").findAll(input.last()).map { it.value }.toList()
        val g = Grid(points, gridX, input.size - 1)
        val columns = (0 until gridX).map { idx -> g[idx] }
        val colNumbers = columns.map { col ->
            col.values.filter { it != ' ' }.joinToString("")
        }.toMutableList().logit("Col numbers")
    }
}

fun main() {
    Day202506().solve(false)
}

// 7450962406039 -- too low
// 7450962406002 -- too low
// 2452968911111283 -- too high
