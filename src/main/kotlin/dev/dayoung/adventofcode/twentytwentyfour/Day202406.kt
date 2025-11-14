package dev.dayoung.adventofcode.twentytwentyfour

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.Vec2i
import dev.dayoung.adventofcode.structures.Grid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.time.measureTime

fun main() {
    Day202406().solve(true)
}

enum class Direction(val dir: Vec2i, val inverse: Vec2i) {
    NORTH(Vec2i.UP, Vec2i.DOWN), SOUTH(Vec2i.DOWN, Vec2i.UP), EAST(Vec2i.RIGHT, Vec2i.LEFT), WEST(Vec2i.LEFT, Vec2i.RIGHT);
    fun next() : Direction =
        when(this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
}

class Day202406 : PuzzleSolution(2024, 6) {
    private val globalVisited = mutableSetOf<Pair<Vec2i, Direction>>()
    private fun travel(grid: Grid<Char>, start: Vec2i, facing: Direction, preVisit: Set<Pair<Vec2i, Direction>> = setOf()): Set<Pair<Vec2i, Direction>> {
        val visited = preVisit.toMutableSet()
        var actor = start
        var movingIn = facing
//        try {
            while(actor + movingIn.dir in grid) {
                if (grid[actor + movingIn.dir] == '#') {
                    movingIn = movingIn.next()
                } else {
                    visited += actor + movingIn.dir to movingIn
                    actor += movingIn.dir
                }
            }
//        } catch(_: IndexOutOfBoundsException) {}

        return visited
    }
    fun partOne(grid: Grid<Char>): Int {
        val facing = Direction.NORTH
        val actor = grid.indexOf('^') ?: return 0
        val visited = travel(grid, actor, facing)
        globalVisited.addAll(visited)
        return visited.unzip().first.toSet().size
    }

    fun partTwo(grid: Grid<Char>): Int {
        var paradoxCount = 0
        val paradoxes = mutableSetOf<Vec2i>()

                /*
                val potentialParadoxes = when(nextFace) {
                    Direction.NORTH-> {
                        val cells = Grid.Column(grid, actor.x)
                        (actor.y downTo  0).takeWhile { cells[it] != '#'}.mapNotNull { y -> if(Vec2i(actor.x, y) in visited) Vec2i(actor.x, y) else null }
                    }
                    Direction.SOUTH -> {
                        val cells = Grid.Column(grid, actor.x)
                        (actor.y until grid.height).takeWhile { cells[it] != '#'}.mapNotNull { y -> if(Vec2i(actor.x, y) in visited) Vec2i(actor.x, y) else null }
                    }
                    Direction.EAST -> {
                        val cells = Grid.Row(grid, actor.y)
                        (actor.x until grid.width).takeWhile { cells[it] != '#'}.mapNotNull { x -> if(Vec2i(x, actor.y) in visited) Vec2i(x, actor.y) else null }
                    }
                    Direction.WEST -> {
                        val cells = Grid.Row(grid, actor.y)
                        (actor.x downTo 0).takeWhile { cells[it] != '#'}.mapNotNull { x -> if(Vec2i(x, actor.y) in visited) Vec2i(x, actor.y) else null }
                    }
                }

                val validParadoxes = potentialParadoxes.firstOrNull {
                    visited[it]?.dir == nextFace.dir
                }
                if(validParadoxes != null) {
                    paradoxCount++
                    println("Put obstacle at ${actor + movement}")
                    paradoxes.add(actor + movement)
                }

                 */
//        println(grid.withVisited(paradoxes, 'O'))
        return paradoxCount
    }
    val parser = { input: List<String> -> Grid(input.joinToString("").toList(), input.first().length, input.size) }

    override fun solve(sampleMode: Boolean) {
        println("2024 - Day 06")
        Utils.readInputResource(sampleMode, "input/2024/six.txt")?.let { lines ->
            val grid = parser(lines)
            measureTime {
                println("Part One: ${partOne(grid)}")
            }.also { println("${it.inWholeMilliseconds} ms") }
            measureTime {
                runBlocking(Dispatchers.Default) {
                    println("Part Two: ${partTwo(grid)}")
                }
            }.also { println("${it.inWholeMilliseconds} ms") }
        }
    }
}
