package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.structures.Grid
import org.springframework.stereotype.Component

@Component
class Day202504 : PuzzleSolution(2025, 4) {
    fun partOne(grid: Grid<Char>): Int {
        val neighborCounts = grid.coords
            .map {
                val nCount = it.allNeighbors.count { grid[it] == '@' }
                if(grid[it] == '.') "." else if(nCount < 4) "x" else "@"
            }
        return neighborCounts.count { it == "x" }
    }
    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2025/04.txt", sampleMode)?.let { input ->
            val gridx = input.first().length
            val gridy = input.size
            val points = input.joinToString("").toList()
            val grid = Grid(points, gridx, gridy, Grid.ReturnDefault('.'))
            log.info { "Accessible rolls: ${partOne(grid)}" }
        }
    }
}

fun main() {
    Day202504().solve()
}
