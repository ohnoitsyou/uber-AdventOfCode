package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.structures.Vec2i
import dev.dayoung.adventofcode.structures.Grid
import dev.dayoung.adventofcode.structures.Vec2iDir
import dev.dayoung.adventofcode.toComboTripleLongBy
import dev.dayoung.adventofcode.structures.toVec2iList
import dev.dayoung.adventofcode.structures.visualizeGridShape
import kotlin.collections.set
import kotlin.math.abs

typealias TileMap = HashMap<Int, TileColor>

enum class TileColor {
    GREEN, RED
}

class Day202509: PuzzleSolution(PUZZLE_YEAR, PUZZLE_DAY) {

    fun colorTilesMk2(points: Pair<Vec2i, Vec2i>, tileMap: TileMap, gridWidth: Int) {
        // Figure out if the points are vertical or horizontal
        // y == y -> Horizontal
        // x == x -> Vertical
        val (first, second) = points
        if(first.y == second.y) {
            // figure out which point is left, which is right
            val (left, right) = if(first.x < second.x) first to second else second to first
            tileMap[left.toArrayIdx(gridWidth)] = TileColor.RED
            tileMap[right.toArrayIdx(gridWidth)] = TileColor.RED
            (1 until (right.x - left.x)).forEach { nx ->
                val np = left + Vec2iDir.RIGHT * nx
                tileMap[np.toArrayIdx(gridWidth)] = TileColor.GREEN
            }
        } else if (first.x == second.x) {
            // figure out which point is top, which is bottom
            val (top, bottom) = if(first.y < second.y) first to second else second to first
            tileMap[top.toArrayIdx(gridWidth)] = TileColor.RED
            tileMap[bottom.toArrayIdx(gridWidth)] = TileColor.RED
            (1 until (bottom.y - top.y)).forEach { ny ->
                val np = top + Vec2iDir.DOWN * ny
                tileMap[np.toArrayIdx(gridWidth)] = TileColor.GREEN
            }
        } else {
            log.info { "Non linear direction... $first -> $second" }
        }
    }

    fun Vec2i.toArrayIdx(maxX: Int): Int = y * (maxX + 1) + x

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
        val points = listOf(Vec2i(2, 2),  Vec2i(7, 2), Vec2i(7, 10), Vec2i(2, 10))
//        val points = listOf(Vec2i(0, 0), Vec2i(5, 0), Vec2i(5, 5), Vec2i(0, 5))
        val maxX = points.maxBy { it.x }.x.logit("Max x")
//        val maxX = (5 + 1).logit("Max X") + 5
        val maxY = points.maxBy { it.y }.y.logit("Max y")
//        val maxY = (5 + 1).logit("Max Y") + 5
        points.logit()
        val pointIdx = points.map { it.toArrayIdx(maxX) }.logit()
        val tiles = mutableListOf<Int>()
        for((x, y) in points) {
//        (0 until maxX).forEach { y ->
//            (0 until maxY).forEach { x ->
            val p = Vec2i(x, y)
            log.info { "Point $p is at index ${p.toArrayIdx(maxX)} -> $y * $maxX + $x" }
            tiles.add(p.toArrayIdx(maxX))
//            }
//        }
        }
        println(buildString {
            (0 .. maxY).forEach { y ->
                (0 .. maxX).forEach { x ->
                    "$x, $y ${Vec2i(x, y).toArrayIdx(maxX)} ${Vec2i(x, y).toArrayIdx(maxX) in tiles}".logit()
                    append(if(Vec2i(x, y).toArrayIdx(maxX) in pointIdx) "# " else ". ")
                }
                appendLine()
            }
        })

        Grid.fromSparseList(pointIdx, maxX, maxY, '.').print()
//        val sparseTileMap = HashMap<Int, TileColor>()
//        for(point in (points + points.first()).zipWithNext()) {
//            colorTilesMk2(point, sparseTileMap, maxX)
//        }
//        val gridString = buildString {
//            append("  ")
//            (0 .. maxX).forEach { x -> append("${x.toString().last()} ")}.also { appendLine() }
//            (0 until maxY).forEach { y ->
////                log.info { "Checking row $y" }
//                append("${y.toString().last()} ")
//                (0 until maxX).forEach { x ->
////                    log.info { "Checking column $x" }
//                    val idx = y * maxY + x
//                    log.info { "idx: $idx -> ${idx in tiles}" }
//                    if (idx in tiles) {
//                        append("# ")
//                    } else {
//                        append(". ")
//                    }
//                }
//                appendLine()
//            }
//        }
//        println(gridString)
//        val grid = Grid.fromSparseList(sparseTileMap.keys, maxX, maxY, '.')
//        grid.print()
    }

    fun partOne(areaCache: List<Triple<Vec2i, Vec2i, Long>>): Long {
        return areaCache.maxBy { it.third }.logit("Points of largest").third
    }

    fun partTwo(points: List<Vec2i>, areaCache: List<Triple<Vec2i, Vec2i, Long>>): Long {
        val maxX = points.maxBy { it.x }.x.logit("Max x")
        val maxY = points.maxBy { it.y }.y.logit("Max y")
        val tiles = HashMap<Int, TileColor>()
        (points + points.first()).let {
            it.first() == it.last()
        }.logit("First is eq to last")
        for(pointPair in (points + points.first()).zipWithNext()) {
            colorTilesMk2(pointPair, tiles, maxX)
        }
        val grid = Grid.fromSparseList(tiles.keys, maxX, maxY, '.')
        grid.print()

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
        (points + points.first()).let {
            it.first() == it.last()
        }.logit("First is eq to last")
        partOne(areaCache).logit("Largest area")
//        partTwo(points, areaCache)
        visualizeGridShape(points, 100_000, "100kGrid.png", 1_000)
//        testTiles()
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
