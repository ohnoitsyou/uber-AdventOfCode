package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.Vec2i
import dev.dayoung.adventofcode.structures.Grid
import dev.dayoung.adventofcode.toComboTripleLongBy
import dev.dayoung.adventofcode.toVec2iList
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlin.collections.set
import kotlin.math.abs

typealias TileMap = HashMap<Int, TileColor>

enum class TileColor {
    GREEN, RED
}

class Day202509: PuzzleSolution(PUZZLE_YEAR, PUZZLE_DAY) {

    fun colorTiles(point: Pair<Vec2i, Vec2i>, tileMap: TileMap, width: Int) {
        val (first, second) = point
        // y == y -> horizontal (x changes)
        // x == y -> vertical  (y changes)
        log.info { "Adding points from $first to $second"}
        if(first.y == second.y) {
            log.info { "Horizontal" }
            val leftX = Vec2i(minOf(first.x, second.x), first.y)
            val rightX = Vec2i(maxOf(first.x, second.x), first.y)
            log.info { "Left X: $leftX ${leftX.toArrayIndex(width)}"}
            log.info { "Right X: $rightX ${rightX.toArrayIndex(width)}"}
//            tileMap[first.y * width + leftX] = TileColor.RED
//            tileMap[first.y * width + rightX] = TileColor.RED

            tileMap[leftX.toArrayIndex(width)] = TileColor.RED
            tileMap[rightX.toArrayIndex(width)] = TileColor.RED
            ((leftX.x + 1) until rightX.x).forEach { x ->
                Vec2i(x, first.y).toArrayIndex(width).logit("Adding point")
                val p = (first.y * width + x).logit("by hand")
                tileMap[p] = TileColor.GREEN
            }
        } else if(first.x == second.x) {
            log.info { "Vertical" }
            val upperY = minOf(first.y, second.y).logit("upper Y")
            val lowerY = minOf(first.y, second.y).logit("lower Y")
//            tileMap[upperY * width + first.x] = TileColor.RED
//            tileMap[lowerY * width + first.x] = TileColor.RED
            tileMap[Vec2i(first.x, upperY).toArrayIndex(width)] = TileColor.RED
            tileMap[Vec2i(first.x, lowerY).toArrayIndex(width)] = TileColor.RED
            ((upperY + 1) until lowerY).forEach { y ->
                tileMap[y * width + first.x] = TileColor.GREEN
            }
        }
    }

    fun checkIfContained(point: Vec2i, tileMap: TileMap, width: Int, height: Int): Boolean {
        return Vec2i.CARDINAL.map { dir ->
            var c = point
            var contained = tileMap.containsKey(c.toArrayIndex(width))
            do {
                c += dir
                if(tileMap.containsKey(c.toArrayIndex(width))) contained = true
            } while(c.x in 0 .. width && c.y in 0 .. height && !contained)
            contained
        }.all { it }
    }

    fun testTiles() {
        val width = 11
        val height = 7
        (0 until height).forEach { y ->
            (0 until width).forEach { x ->
                val p = Vec2i(x, y)
                log.info { "Point $p is at index ${p.toArrayIndex(width)}"}
            }
        }
    }

    fun partOne(areaCache: List<Triple<Vec2i, Vec2i, Long>>): Long {
        return areaCache.maxBy { it.third }.third
    }

    suspend fun partTwo(points: List<Vec2i>, areaCache: List<Triple<Vec2i, Vec2i, Long>>): Long {
        val maxX = points.maxBy { it.x }.x.logit("Max x")
        val maxY = points.maxBy { it.y }.y.logit("Max y")
        val tiles = HashMap<Int, TileColor>()
        for(pointPair in points.zipWithNext()) {
            colorTiles(pointPair, tiles, maxX)
        }
        // Handle the wrap around
        colorTiles(points.first() to points.last(), tiles, maxX)
        // Convert to grid so we can visualize
        val g = Grid.fromSparseList(tiles.keys.toList(), maxX, maxY, '.')
        g.points.logit()
        g.print()
//        points.all { tiles[it.y * maxX + it.x] == TileColor.RED}.logit("All Corners are RED")
        val validCorners = areaCache.asFlow().filter { (p1, p2, _) ->
            val (c1, c2) = Vec2i(p1.x, p2.y) to Vec2i(p2.x, p1.y)
            checkIfContained(c1, tiles, maxX, maxY).logit("Is contained in area")
//            (tiles.containsKey(c1.toArrayIndex(maxX)) && tiles.containsKey(c2.toArrayIndex(maxX)))//.logit("$c1 -> $c2 -> $this")
        }.toList()
        val validAreas = validCorners.map { Triple(it.first, it.second, it.first.areaBetween(it.second)) }.logit("Contained corners")
        areaCache.size.logit("areaCache size")
        validCorners.size.logit("validCorners size")
        validAreas.maxBy { it.third }.logit("Maximum area of contained")
        return 0L
    }
    override fun solve(sampleMode: Boolean) {
        val points = Utils.readInputResource("2025/09.txt", sampleMode)?.toVec2iList()
        val areaCache = points!!.toComboTripleLongBy { i: Vec2i, j: Vec2i -> abs(i.x - j.x + 1).toLong() * abs(i.y - j.y + 1).toLong() }
//        val areaCache = points!!.flatMapIndexed { i, iPoint ->
//            points.subList(i + 1, points.size).map { jPoint ->
//                Triple(iPoint, jPoint, abs(iPoint.x - jPoint.x + 1).toLong() * abs(iPoint.y - jPoint.y + 1).toLong())
//            }
//        }
//        partOne(areaCache).logit("Largest area")
        testTiles()
        runBlocking {
            partTwo(points, areaCache)
        }
    }

    companion object {
        private const val PUZZLE_YEAR = 2025
        private const val PUZZLE_DAY = 9
    }
}

fun main() = Day202509().solve(true)


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

//  0 1 2 3 4 5 6 7 8 9 0 1
//0 . . . . . . . . . . . .
//1 . . . . . . . # # # # #
//2 . . . . . . . # . . . #
//3 . . # # # # # # . . . #
//4 . . # . . . . . . . . #
//5 . . # # # # # # # # . #
//6 . . . . . . . . . # . #
//7 . . . . . . . . . # # #

//   0 1 2 3 4 5 6 7
// 0 . . . # . # . .
// 1 . . . . # . # .
// 2 . . . . . # . #
// 3 . # . . . . # .
// 4 # . # . . . . #
// 5 . # . # . . . .
// 6 . . . . . . . #
// 7 . . . . . . . .
// 8 # . . . . . . .
// 9 . # . . . . . .
// 0 . . # . . # . .
// 1 . . . # . . # .
// 2 . . . . . # . #
// 3 . . . . . . # .
// 4 # . . . .
// 5 * * * * * * * *
