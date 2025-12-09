package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.Vec2i
import dev.dayoung.adventofcode.structures.Grid
import dev.dayoung.adventofcode.structures.MutableGrid

class Day202507: PuzzleSolution(2025, 7) {
    fun partOne(grid: MutableGrid<Char>): Int {
        // find the start of the "tree"
        val start = grid.points.indexOf('S')
        var row = 0
        var splitCount = 0
        var beams = mutableSetOf(Vec2i(start, 0))
        while(row < grid.height - 1) {
            beams.filter { it.y == row }.forEach { beam ->
                val np = beam + Vec2i.DOWN
                if( grid[np] == '.') {
                    grid[np] = '|'
                    beams.add(np)
                } else if(grid[np] == '^') {
                    splitCount++
                    grid[np + Vec2i.LEFT] = '|'
                    grid[np + Vec2i.RIGHT] = '|'
                    beams.addAll(listOf(np + Vec2i.LEFT, np + Vec2i.RIGHT))
                }
            }
            grid.print()
            row++
        }
        grid.print()
        return splitCount
    }

    // This implementation works from the top down, which is super inefficient because you are following several paths many times.
    // A better solution would be to find all the splits, work from bottom up and for each split, add the number of previous destinations.
    // Then the value at the top should be the final count
    // This implementation also OOM'd
    fun partTwo(grid: MutableGrid<Char>): Int {
        val start = Vec2i(grid.points.indexOf('S'), 0)
        val column = grid[start.x]
        val splitY = column.values.indexOfFirst { it == '^' }
        val firstSplit = Vec2i(start.x, splitY)
        val stack = ArrayDeque(listOf(firstSplit + Vec2i.LEFT, firstSplit + Vec2i.RIGHT))
        var timelines = 0
        while(stack.isNotEmpty()) {
            var current = stack.removeFirst()
            while(grid[current] != '^' && current.y <= grid.height) {
                grid[current] = '|'
                current += Vec2i.DOWN
            }
            if(grid[current] == '^') {
                stack.addAll(listOf(current + Vec2i.LEFT, current + Vec2i.RIGHT))
            } else {
                // must have reached the bottom
                timelines++.logit("Bottom count")
            }
        }
        return timelines
    }

    fun partTwoReverse(grid: Grid<Char>): Int {
        grid.getRow(grid.height - 2).cells.logit()
        // knowing that the last row is full dots...
        val splitCache = mutableMapOf<Vec2i, Long>()
        // seed the cache with the first row
        grid.getRow(grid.height - 2).cells.forEach { (point, _) ->
            if(grid[point] == '^') {
                splitCache.getOrPut(point) { 2 }
            }
        }
        for(row in grid.height - 3 downTo 0) {
            val r = grid.getRow(row)
            r.cells.forEach {(point, v) ->
                if(v == '^') {
                    var left = point + Vec2i.LEFT
                    while(!splitCache.keys.contains(left) && left.y <= grid.height) {
                        left += Vec2i.DOWN
                    }
                    if(splitCache.keys.contains(left)) {
                        left.logit("Found previous left point")
                        val nvL = splitCache.getOrPut(point) { 0 }
                        splitCache[point] = nvL + splitCache[left]!!
                    } else {
                        // we've hit the bottom
                        val nvL = splitCache.getOrDefault(point, 0)
                        splitCache[point] = nvL + 1
                    }

                    var right = point + Vec2i.RIGHT
                    while(!splitCache.keys.contains(right) && right.y <= grid.height) {
                        right += Vec2i.DOWN
                    }
                    if(splitCache.keys.contains(right)) {
                        right.logit("Found previous right point")
                        val nvR = splitCache.getOrPut(point) { 0 }
                        splitCache[point] = nvR + splitCache[right]!!
                    } else {
                        // we've hit the bottom
                        val nvR = splitCache.getOrDefault(point, 0)
                        splitCache[point] = nvR + 1
                    }
                } else if(v == 'S') {
                    splitCache.logit("Final cache")
                }
            }
        }

        return 0
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2025/07.txt", sampleMode)?.let { input ->
//            partOne(MutableGrid(input.joinToString("").toMutableList(), input.first().length, input.size)).logit("Split count")
            partTwoReverse(MutableGrid(input.joinToString("").toMutableList(), input.first().length, input.size, Grid.ReturnDefault('.'))).logit("Timeline count")
        }
    }
}

fun main() = Day202507().solve(false)
