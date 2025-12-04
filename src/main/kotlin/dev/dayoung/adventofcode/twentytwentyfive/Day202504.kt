package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.Vec2i
import dev.dayoung.adventofcode.structures.Grid
import dev.dayoung.adventofcode.structures.MutableGrid
import org.springframework.stereotype.Component

@Component
class Day202504 : PuzzleSolution(2025, 4) {
    fun findAccessibleRolls(grid: Grid<Char>): List<Vec2i> {
        return grid.coords.filter { grid[it] == '@' }
            .filter { it.allNeighbors.count { grid[it] == '@' } < 4 }
    }

    fun partOne(grid: Grid<Char>): Int {
        return findAccessibleRolls(grid).count()
    }

    fun partTwo(grid: MutableGrid<Char>): Int {
        var totalRolls = 0;
        var accessibleRolls = findAccessibleRolls(grid)
        while(accessibleRolls.count() != 0) {
            totalRolls += accessibleRolls.count()
            accessibleRolls.forEach { grid[it] = '.' }
            accessibleRolls = findAccessibleRolls(grid)
        }
        return totalRolls
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2025/04.txt", sampleMode)?.let { input ->
            val gridX = input.first().length
            val gridY = input.size
            val points = input.joinToString("").toMutableList()
            val grid = MutableGrid(points, gridX, gridY, Grid.ReturnDefault('.'))
            log.info { "Accessible rolls: ${partOne(grid)}" }
            log.info { "Accessible rolls: ${partTwo(grid)}" }
        }
    }
}
