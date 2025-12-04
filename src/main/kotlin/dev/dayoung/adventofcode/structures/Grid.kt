package dev.dayoung.adventofcode.structures

import dev.dayoung.adventofcode.Vec2i
import dev.dayoung.adventofcode.Vec2iV
import kotlin.math.abs


open class Grid<T>(open val points: List<T>, val width: Int, val height: Int, open val oobBehavior: OOBBehavior<T> = Throw()) {
    sealed interface OOBBehavior<T> {
        fun oob(grid: Grid<T>, point: Vec2i): Pair<Vec2i, T>
    }
    class Throw<T> : OOBBehavior<T> {
        override fun oob(grid: Grid<T>, point: Vec2i): Pair<Vec2i, T> {
            throw IndexOutOfBoundsException("Out of bounds")
        }
    }

    class ReturnDefault<T>(private val default: T): OOBBehavior<T> {
        override fun oob(grid: Grid<T>, point: Vec2i): Pair<Vec2i, T> {
            return point to default
        }
    }

    class Loop<T>(): OOBBehavior<T> {
        override fun oob(grid: Grid<T>, point: Vec2i): Pair<Vec2i, T> {
            val x = point.x % grid.width
            val y = point.y % grid.height
            return Vec2i(x, y) to grid[x, y]
        }
    }

    val coords by lazy {
        (0 until height).flatMap { y -> (0 until width).map { x -> Vec2i(x, y) } }
    }

    open class Column<T>(private val grid: Grid<T>, val x: Int) {
        operator fun get(y: Int) = grid[x, y]
        val cells: Collection<Pair<Vec2i, T>> get() = (0 until grid.height).map { y -> Vec2i(x, y) to grid[x][y] }
        val values: Collection<T> get() = (0 until grid.height).map { y -> grid[x][y] }
    }

    open class Row<T>(private val grid: Grid<T>, val y: Int) {
        operator fun get(x: Int) = grid[x, y]
        val cells: Collection<Pair<Vec2i, T>> get() = (0 until grid.width).map { x -> Vec2i(x, y) to grid[x][y] }
        val values: Collection<T> get() = (0 until grid.width).map { x -> grid[x][y] }
    }

    operator fun get(x: Int, y: Int): T {
        return if (x in 0 until width && y in 0 until height) {
            points[y * width + x]
        } else {
          oobBehavior.oob(this, Vec2i(x, y)).second
        }
    }
    operator fun get(c: Vec2i): T = get(c.x, c.y)

    // get col, row
    operator fun get(x: Int) = Column(this, x)
    fun getRow(y: Int) = Row(this, y)

    operator fun contains(c: Vec2i): Boolean = c.y * width + c.x in 0 until width * height

    fun indexOf(target: T): Vec2i? {
        return points.indexOf(target).let {
            if (it == -1) return null
            Vec2i(it % width, it / width)
        }
    }

    open fun print() {
        repeat(width) { print("* ") }.also { println() }
        points.chunked(width).map { it.joinToString(" ") }.forEach(::println)
        repeat(width) { print("* ") }.also { println() }
    }

    fun printVisited(visited: Set<Vec2i>) {
        points.chunked(width).forEachIndexed { yidx, y ->
            y.forEachIndexed { xidx, x ->
                val point = Vec2i(xidx, yidx)
                if(point in visited) {
                    print("X ")
                } else {
                    print("$x ")
                }
            }
            println()
        }
    }
    override fun toString(): String {
        return buildString {
            repeat(width) { append("* ") }.also { appendLine() }
            points.chunked(width).map { it.joinToString(" ") }.forEach(::appendLine)
            repeat(width) { append("* ") }.also { appendLine() }
        }
    }

}

class MutableGrid<T>(override val points: MutableList<T>, width: Int, height : Int, oobBehavior: OOBBehavior<T> = Throw()) : Grid<T>(points, width, height, oobBehavior) {
    operator fun set(point: Vec2i, value: T) {
        val p = point.y * width + point.x
        if (p <= this.points.lastIndex) {
            points[p] = value
        } else {
          if (oobBehavior is Loop) {
            val loopPoint: Vec2i = oobBehavior.oob(this, Vec2i(p, p)).first
            val idx = loopPoint.y * width + loopPoint.x
            points[idx] = value
          } else {
            oobBehavior.oob(this, Vec2i(p, p))
          }
        }
    }
}

open class ObstacleGrid<Char>(override val points: MutableList<Char>, width: Int, height: Int, oobBehavior: OOBBehavior<Char> = Throw(), val obstacleGenerator: (Vec2i) -> Boolean) : Grid<Char>(points, width, height, oobBehavior) {
    companion object {
        const val MAX_MOVE_COST = Integer.MAX_VALUE
    }

    private val heightRange: IntRange = (0 until height)
    private val widthRange: IntRange = (0 until width)

    val obstacleCache = mutableMapOf<Vec2i, Boolean>().withDefault { false }

    init {
        points.chunked(width).forEachIndexed { yidx, y -> y.forEachIndexed { xidx, c ->
            obstacleCache[Vec2i(xidx, yidx)] = (c == '#')
        }}
    }

    open fun isObstacle(point: Vec2i): Boolean {
        return obstacleCache.getOrPut(point) {
            obstacleGenerator(point)
        }
    }

    open fun moveCost(from: Vec2i, to: Vec2i) = if (isObstacle(to)) MAX_MOVE_COST else 1

    private val validMoves = Vec2i.CARDINAL

    fun getNeighbours(position: Vec2i): List<Vec2i> = validMoves
        .map { Vec2i(position.x + it.x, position.y + it.y) }
        .filter { inGrid(it) }

    private fun inGrid(it: Vec2i) = (it.x in widthRange) && (it.y in heightRange)

    fun print(path: List<Vec2i> = listOf()) {
        repeat(width) { print("*") }.also { println() }
        (0 until height).forEach { y ->
            (0 until width).forEach { x ->
                val p = Vec2i(x, y)
                val c = if (isObstacle(p)) {
                    "#"
                } else if (p in path) {
                    "O"
                } else {
                    "."
                }
                print(c)
            }.also { println() }
        }
        repeat(width) { print("*") }.also { println() }
    }
}

fun Grid<Char>.withVisited(visited: Set<Vec2i>, visitChar: Char = 'X'): Grid<Char> {
    return Grid(this.points.chunked(width).flatMapIndexed { yidx, y ->
        y.mapIndexed { xidx, x ->
            if(Vec2i(xidx, yidx) in visited) { visitChar } else { x }
        }
    }.toMutableList(), width, height)
}

fun MutableGrid<Char>.setRectangle(p1: Vec2i, p2: Vec2i, char: Char) {
    (minOf(p1.x, p2.x) .. maxOf(p1.x, p2.x)).forEach { x ->
        (minOf(p1.y, p2.y) .. maxOf(p1.y, p2.y)).forEach { y ->
            this[Vec2i(x, y)] = char
        }
    }
}

fun MutableGrid<Char>.rotateColumn(x: Int, depth: Int) {
    Grid.Column(this, x).cells.forEach { (point, value) ->
        val wrappedPoint = Vec2i(point.x, (point + Vec2i(0, depth)).y % height)
        this[wrappedPoint] = value
    }
}

fun MutableGrid<Char>.rotateRow(y: Int, depth: Int) {
    Grid.Row(this, y).cells.forEach { (point, value) ->
        val wrappedPoint = Vec2i((point + Vec2i(depth, 0)).x % width, point.y)
        this[wrappedPoint] = value
    }
}
