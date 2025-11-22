package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.Vec2i
import dev.dayoung.adventofcode.structures.Grid
import dev.dayoung.adventofcode.structures.MutableGrid
import dev.dayoung.adventofcode.structures.rotateColumn
import dev.dayoung.adventofcode.structures.rotateRow
import dev.dayoung.adventofcode.structures.setRectangle
import org.springframework.stereotype.Component

@Component
class Day201608 : PuzzleSolution(2016, 8) {
    val rect = Regex("""rect (\d+)x(\d+)""")
    val rRow = Regex("""rotate row y=(\d+) by (\d+)""")
    val rCol = Regex("""rotate column x=(\d+) by (\d+)""")

    fun partOne(grid: MutableGrid<Char>, input: List<String>): Int {
        for (line in input) {
            rect.matchEntire(line)?.destructured?.let {
                val (x, y) = it.toList().map { i -> Integer.parseInt(i) }
                grid.setRectangle(Vec2i(0,0), Vec2i(x - 1, y - 1), '#')
                continue
            }
            rRow.matchEntire(line)?.destructured?.let {
                val (row, dist) = it.toList().map { i -> Integer.parseInt(i) }
                grid.rotateRow(row, dist)
                continue
            }
            rCol.matchEntire(line)?.destructured?.let {
                val (col, dist) = it.toList().map { i -> Integer.parseInt(i) }
                grid.rotateColumn(col, dist)
                continue
            }
        }
        return grid.points.count { it == '#' }
    }

    fun partTwo(grid: MutableGrid<Char>) {
        grid.print()
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource(sampleMode, "2016/eight.txt")?.let { lines ->
            val gridx = lines.first().substringBefore('x').toInt()
            val gridy = lines.first().substringAfter('x').toInt()
            val points  = CharArray(gridx * gridy) { '.' }.toMutableList()
            val grid = MutableGrid(points, gridx, gridy, Grid.Loop())
            println("Lights on: ${partOne(grid, lines.drop(1))}")
            partTwo(grid)
        }
    }
}
