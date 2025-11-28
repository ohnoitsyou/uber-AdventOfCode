package dev.dayoung.adventofcode.structures

import dev.dayoung.adventofcode.Vec2i
import dev.dayoung.adventofcode.Vec2iV

//typealias GridPosition = Vec2i

class AStarNav {
    fun generatePath(currentPos: Vec2i, previous: Map<Vec2i, Vec2i>): List<Vec2i> {
        val path = mutableListOf(currentPos)
        var current = currentPos
        while(previous.containsKey(current)) {
            current = previous.getValue(current)
            path.add(0, current)
        }
        return path.toList()
    }

    fun aStarSearch(start: Vec2i, goal: Vec2i, grid: ObstacleGrid<Char>): Pair<List<Vec2i>, Int> {
        val openSet = mutableSetOf(start)
        val closedSet = mutableSetOf<Vec2i>()
        val costFromStart = (mutableMapOf(start to 0)).withDefault { _ -> ObstacleGrid.MAX_MOVE_COST }
        val estimatedTotal = mutableMapOf(start to start.heuristicDistance(goal))

        val cameFrom = mutableMapOf<Vec2i, Vec2i>()
        while(openSet.isNotEmpty()) {
            val currentPos = openSet.minBy { estimatedTotal.getValue(it) }
            if (currentPos == goal) {
                val path = generatePath(currentPos, cameFrom)
                return Pair(path, estimatedTotal.getValue(goal))
            }
            openSet.remove(currentPos)
            closedSet.add(currentPos)

            val neighbors = Vec2i.CARDINAL.map { currentPos + it }
            val validNeighbors = neighbors.filterNot { closedSet.contains(it) }
                .filter { it.x in 0 until grid.width && it.y in 0 until grid.height }
            println("$currentPos : $validNeighbors")
            validNeighbors.forEach { neighbor ->
                    val score = costFromStart.getValue(currentPos) + grid.moveCost(currentPos, neighbor)
                    if (score < costFromStart.getValue(neighbor)) {
                        if(!openSet.contains(neighbor)) {
                            openSet.add(neighbor)
                        }
                        cameFrom[neighbor] = currentPos
                        costFromStart[neighbor] = score
                        estimatedTotal[neighbor] = score + neighbor.heuristicDistance(goal)
                    }
                }
        }

        throw IllegalStateException("No path from start $start to finish $goal")
    }

    fun aStarSearch2(start: Vec2i, finish: Vec2i, grid: ObstacleGrid<Char>): Pair<List<Vec2i>, Int> {
        val openSet = mutableSetOf(start)
        val closedSet = mutableSetOf<Vec2i>()
        val costFromStart = mutableMapOf(start to 0).withDefault { ObstacleGrid.MAX_MOVE_COST }
        val estimatedTotalCost = mutableMapOf(start to start.heuristicDistance(finish))

        val cameFrom = mutableMapOf<Vec2i, Vec2i>()  // Used to generate path by back tracking

        while(openSet.isNotEmpty()) {
            val currentPos = openSet.minBy { estimatedTotalCost.getValue(it) }

            // Check if we have reached the finish
            if (currentPos == finish) {
                // Backtrack to generate the most efficient path
                val path = generatePath(currentPos, cameFrom)
                return Pair(path, estimatedTotalCost.getValue(finish)) // First Route to finish will be optimum route
            }

            openSet.remove(currentPos)
            closedSet.add(currentPos)

            val neighbors = grid.getNeighbours(currentPos)
            neighbors.filterNot { closedSet.contains(it) }  // Exclude previous visited vertices
                .forEach { neighbour ->
                    val score = costFromStart.getValue(currentPos) + grid.moveCost(currentPos, neighbour)
                    if (score < costFromStart.getValue(neighbour)) {
                        if (!openSet.contains(neighbour)) {
                            openSet.add(neighbour)
                        }
                        cameFrom[neighbour] = currentPos
                        costFromStart[neighbour] = score
                        estimatedTotalCost[neighbour] = score + neighbour.heuristicDistance(finish)
                    }
                }
        }

        throw IllegalArgumentException("No Path from Start $start to Finish $finish")
    }
}

fun main() {
    fun barrierGenerator(point: Vec2i, seed: Int = 0): Boolean {
        return Integer.toBinaryString((point.x * point.x) + (3 * point.x) + (2 * point.x * point.y) + point.y + (point.y * point.y) + seed)
            .also { println("$point : $it") }
            .count { it == '1'} % 2 == 0
    }

    val points = (0 until 7).flatMap { y ->
        (0 until 10).map { x ->
            val p = Vec2i(x, y)
            val c = if (barrierGenerator(p, 10)) "." else "#"
            Vec2iV(p, c)
        }
    }

    println(buildString {
        points.chunked(10).map { it.joinToString("") }.forEach(::appendLine)
    })

    val ps = points.map { it.value.first() }.toMutableList()
    val grid = ObstacleGrid(ps, 10, 7, Grid.Throw()) { p-> barrierGenerator(p) }

    val path = AStarNav().aStarSearch2(Vec2i(1, 1), Vec2i(7, 4), grid)
    println(path)
    println(path.first.size)
    grid.print(path.first)

//    val points = CharArray(10 * 7) { '.' }.toMutableList()
//    val grid = ObstacleGrid(points, 10, 7, Grid.Throw()) { point -> barrierGenerator(point, 10) }
//    grid.print(listOf())
//    val path = AStarNav().aStarSearch(Vec2i(1, 1), Vec2i(3, 1), grid)
//    grid.print(path.first)
//    AStarNav().aStarSearch(Vec2i(1, 1), Vec2i(7, 4), grid)
//
//    assert(!barrierGenerator(Vec2i(1, 0), 10))
//    assert(barrierGenerator(Vec2i(1,1 ), 10))
}
