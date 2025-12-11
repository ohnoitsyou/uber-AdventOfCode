package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.Vec2i
import dev.dayoung.adventofcode.toVec2iList
import kotlin.collections.set
import kotlin.math.abs

typealias TileMap = HashMap<Int, TileColor>

enum class TileColor {
    GREEN, RED
}

class Day202509: PuzzleSolution(PUZZLE_YEAR, PUZZLE_DAY) {
    fun partOne(areaCache: List<Triple<Vec2i, Vec2i, Long>>): Long {
        return areaCache.maxBy { it.third }.third
    }

    fun colorTiles(point: Pair<Vec2i, Vec2i>, tileMap: TileMap, maxX: Int) {
        val (first, second) = point
        // y == y -> horizontal (x changes)
        // x == y -> vertical  (y changes)
        if(first.y == second.y) {
            val leftX = minOf(first.x, second.x)
            val rightX = maxOf(first.x, second.x)
            tileMap[first.y * maxX + leftX] = TileColor.RED
            tileMap[first.y * maxX + rightX] = TileColor.RED
            ((leftX + 1) until rightX).forEach { x ->
                tileMap[first.y * maxX + x] = TileColor.GREEN
            }
        } else if(first.x == second.x) {
            val upperY = minOf(first.y, second.y)
            val lowerY = minOf(first.y, second.y)
            tileMap[upperY * maxX + first.x] = TileColor.RED
            tileMap[lowerY * maxX + first.x] = TileColor.RED
            ((upperY + 1) until lowerY).forEach { y ->
                tileMap[y * maxX + first.x] = TileColor.GREEN
            }
        }
    }
    fun partTwo(points: List<Vec2i>, areaCache: List<Triple<Vec2i, Vec2i, Long>>): Long {
        val maxX = points.maxBy { it.x }.x
        val maxY = points.maxBy { it.y }.y
        val tiles = HashMap<Int, TileColor>()
        val pointPairs = ArrayDeque(points.zipWithNext())
        for(point in pointPairs) {
            colorTiles(point, tiles, maxX)
        }
        // Handle the wrap around
        colorTiles(points.first() to points.last(), tiles, maxX)
//        points.all { tiles[it.y * maxX + it.x] == TileColor.RED}.logit("All Corners are RED")
        return 0L
    }
    override fun solve(sampleMode: Boolean) {
        val points = Utils.readInputResource("2025/09.txt", sampleMode)?.toVec2iList()
        val areaCache = points!!.flatMapIndexed { i, iPoint ->
            points.subList(i + 1, points.size).map { jPoint ->
                Triple(iPoint, jPoint, abs(iPoint.x - jPoint.x + 1).toLong() * abs(iPoint.y - jPoint.y + 1).toLong())
            }
        }
//        partOne(areaCache).logit("Largest area")
        partTwo(points, areaCache)
    }

    companion object {
        private const val PUZZLE_YEAR = 2025
        private const val PUZZLE_DAY = 9
    }
}

fun main() = Day202509().solve(false)


//. . . . . . . . . . . . . . . . . .
//. . . . . . . . . . . . . . . . . .
//. . X . . . . X . . . . . . . . . .  (2, 2)  (7, 2)
//. . . . . . . . . . . . . . . . . .
//. . . . . . . . . . . . . . . . . .
//. . . . . . . . . . . . . . . . . .
//. . . . . . . . . . . . . . . . . .
//. . . . . . . . . . . . . . . . . .
//. . . . . . . . . . . . . . . . . .
//. . X . . . . X . . . . . . . . . .  (2, 10) (7, 10)
//. . . . . . . . . . . . . . . . . .
//. . . . . . . . . . . . . . . . . .
