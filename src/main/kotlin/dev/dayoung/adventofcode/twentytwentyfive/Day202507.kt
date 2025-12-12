package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.structures.Vec2i
import dev.dayoung.adventofcode.structures.Grid
import org.springframework.stereotype.Component

@Component
class Day202507: PuzzleSolution(2025, 7) {
    fun partOne(grid: Grid<Char>): Int {
        // find the start of the "tree"
        val start = grid.points.indexOf('S')
        var row = 0
        var splitCount = 0
        val beams = mutableSetOf(Vec2i(start, 0))
        while(row < grid.height - 1) {
            beams.filter { it.y == row }.forEach { beam ->
                val np = beam + Vec2i.DOWN
                if( grid[np] == '.') {
                    beams.add(np)
                } else if(grid[np] == '^') {
                    splitCount++
                    beams.addAll(listOf(np + Vec2i.LEFT, np + Vec2i.RIGHT))
                }
            }
            row++
        }
        return splitCount
    }

    // This logic could probably use some work, but I'm ok with it for now.
    fun findParentPoint(grid: Grid<Char>, cache: MutableMap<Vec2i, Long>, parent: Vec2i, start: Vec2i) {
        var p = start
        while(!cache.keys.contains(p) && p.y <= grid.height) {
            p += Vec2i.DOWN
        }
        if(cache.keys.contains(p)) {
            val nvL = cache.getOrPut(parent) { 0 }
            cache[parent] = nvL + cache[p]!!
        } else {
            // we've hit the bottom
            val nvL = cache.getOrDefault(parent, 0)
            cache[parent] = nvL + 1
        }
    }

    /**
     * Start at the bottom of the tree. Seed the cache with the last row of splits,
     *   each of those has two paths to the bottom
     * Moving up the tree, find each split and trace it back down until we either find a split or the bottom.
     * If we find a split, take the value of that split from the cache and store it on ourselves.
     * Do this for both left and right of the original split.
     * If we hit the bottom, use the value 1.
     * This cumulative value means we don't have to track back to the root every time.
     * The final number of 'timelines' is the value from the cache node near the top of the tree, the first split.
     */
    fun partTwo(grid: Grid<Char>): Long {
        val splitCache = mutableMapOf<Vec2i, Long>()
        // knowing that the last row is full dots...
        // seed the cache with the second to last row
        grid.getRow(grid.height - 2).cells.forEach { (point, _) ->
            if(grid[point] == '^') {
                splitCache.getOrPut(point) { 2 }
            }
        }
        for(row in grid.height - 3 downTo 0) {
            grid.getRow(row).cells.forEach {(point, v) ->
                if(v == '^') {
                    val left = point + Vec2i.LEFT
                    findParentPoint(grid, splitCache, point, left)

                    val right = point + Vec2i.RIGHT
                    findParentPoint(grid, splitCache, point, right)
                }
            }
        }
        return splitCache.entries.minBy { (vec, _) -> vec.y }.value
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2025/07.txt", sampleMode)?.let { input ->
            partOne(Grid(input.joinToString("").toMutableList(), input.first().length, input.size)).logit("Split count")
            partTwo(Grid(input.joinToString("").toMutableList(), input.first().length, input.size, Grid.ReturnDefault('.'))).logit("Timeline count")
        }
    }
}
