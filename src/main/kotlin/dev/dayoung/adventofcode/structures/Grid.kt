package dev.dayoung.adventofcode.structures

import dev.dayoung.adventofcode.Vec2i

open class Grid<T>(val points: List<T>, val width: Int, val height: Int) {
    init {
        require(points.size == width * height) { "Number of points (${points.size} doesn't match given height (${height}) and width (${width})" }
    }

    val coords = (0 until height).flatMap { y -> (0 until width) }

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
        if( x < 0 || x > width || y < 0 || y > height) {
            throw IndexOutOfBoundsException()
        }

        return points[y * width + x]
    }
    operator fun get(c: Vec2i): T = get(c.x, c.y)

    // get col, row
    open operator fun get(x: Int) = Column(this, x)
    open fun getRow(y: Int) = Row(this, y)

    operator fun contains(c: Vec2i): Boolean = c.y * width + c.x in 0 until width * height

    fun indexOf(target: T): Vec2i? {
        return points.indexOf(target).let {
            if (it == -1) return null
            Vec2i(it % width, it / width)
        }
    }

    fun print() {
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
fun Grid<Char>.withVisited(visited: Set<Vec2i>, visitChar: Char = 'X'): Grid<Char> {
    return Grid(this.points.chunked(width).flatMapIndexed { yidx, y ->
        y.mapIndexed { xidx, x ->
            if(Vec2i(xidx, yidx) in visited) { visitChar } else { x }
        }
    }, width, height)
}
