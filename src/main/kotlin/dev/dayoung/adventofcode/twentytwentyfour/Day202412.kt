package dev.dayoung.adventofcode.twentytwentyfour

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.structures.Vec2i
import dev.dayoung.adventofcode.structures.Vec2iV
import dev.dayoung.adventofcode.structures.Grid
import dev.dayoung.adventofcode.structures.toVec2iVList

fun main() {
    Day202412().solve(true)
}

class Day202412: PuzzleSolution(2024, 12, true) {

    fun partOne(grid: Grid<Vec2iV<Char>>): Int {
        val perimeter = 0
        val area = 0
        grid[Vec2i(0,0)]
        return 0
    }

    fun findRegion() {

    }

    val parser = { input: List<String> -> Grid(input.toVec2iVList(), input.first().length, input.size, Grid.ReturnDefault(Vec2iV(Vec2i(-1, -1,), '0'))) }

    override fun solve(sampleMode: Boolean) {
        log.info { "2024 - Day 12" }
        Utils.readInputResource("2024/12-1.txt", sampleMode)?.let { parser(it) }?.let { grid ->
            partOne(grid)
        }
    }
}
