package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.structures.Grid
import org.springframework.stereotype.Component

@Component
class Day202506 : PuzzleSolution(2025, 6) {
    val lineRx = Regex("""([0-9]+)+?""")
    val operRx = Regex("""([*+])""")

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2025/06.txt", sampleMode)?.let { lines ->
            partOne(lines).logit("Part One")
            partTwo(lines).logit("Part Two")
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
        }.sum()
    }

    fun partTwo(input: List<String>): Long {
        val gridX = input.first().length.logit("gridX")
        val points = input.dropLast(1).joinToString("").toList().logit("points")
        val operations = Regex("""([*+]) +""").findAll(input.last()).map { it.value }.toList()
        val g = Grid(points, gridX, input.size - 1)
        val columns = (0 until gridX).map { idx -> g[idx] }
        val colNumbers = columns.map { col ->
            col.values.filter { it != ' ' }.joinToString("")
        }.toMutableList().logit("Col numbers")

        var total = 0L
        var revCols = colNumbers.reversed().logit("Rev Col numbers")
        var revOper = operations.reversed().map { it.trim() }.logit("Rev Oper")
        while(revCols.isNotEmpty()) {
            val numbers = revCols.takeWhile { it != "" }.map { it.toLong() }
            revCols = revCols.drop(numbers.size).dropWhile { it == "" }
            val op = revOper.first()
            revOper = revOper.drop(1)
            total += when (op.trim()) {
                "+" -> numbers.sum()
                "*" -> numbers.fold(1L) { acc, i -> acc * i }
                else -> 0L.also { log.info { "##### invalid operation: $op #####" } }
            }
        }
        return total
    }
}
