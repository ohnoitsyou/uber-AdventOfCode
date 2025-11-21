package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Vec2i
import dev.dayoung.adventofcode.structures.Grid
import dev.dayoung.adventofcode.structures.MutableGrid
import dev.dayoung.adventofcode.structures.rotateColumn
import dev.dayoung.adventofcode.structures.setRectangle

class Day201608 : PuzzleSolution(2016, 8) {
    fun partOne(input: List<String>): Int {
        val numPoints = 50 * 6
        val points  = CharArray(numPoints) { '.' }.toMutableList()
        val grid = MutableGrid(points, 50, 6, Grid.Loop())
        grid.print()
        grid.setRectangle(Vec2i(0, 0), Vec2i(2, 1), '#')
        grid.rotateColumn(0, 2)
        grid.print()
        return 0
    }

    override fun solve(sampleMode: Boolean) {
        partOne(listOf())
    }
}

fun main() {
    Day201608().solve(false)
}
