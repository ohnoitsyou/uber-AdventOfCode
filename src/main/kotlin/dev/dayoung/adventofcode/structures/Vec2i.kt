package dev.dayoung.adventofcode.structures

import kotlin.math.abs

enum class Vec2iDir(val dx: Int, val dy: Int) {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(1, 0),
    RIGHT(1, 0);
    infix fun mag(value: Int): Vec2i = Vec2i(dx * value, dy * value)
    infix operator fun times(value: Int): Vec2i = Vec2i(dx * value, dy * value)
//    infix operator fun plus(other: Vec2i): Vec2i = Vec2i(dx + other.x, + dy + other.y)
}

data class Vec2i(val x: Int, val y: Int) {
    operator fun plus(other: Vec2i): Vec2i { return Vec2i(x + other.x, y + other.y) }
    operator fun minus(other: Vec2i): Vec2i { return Vec2i(x - other.x, y - other.y) }

    infix fun mag(mult: Int): Vec2i { return Vec2i(x * mult, y * mult) }

    val linearNeighbors: List<Vec2i>
        get() = CARDINAL.map { this + it}
    val diagonalNeighbors: List<Vec2i>
        get() = DIAGONALS.map { this + it }
    val allNeighbors: List<Vec2i>
        get() = CARDINAL.map { this + it } + DIAGONALS.map { this + it }

    fun manhattanDistance(other: Vec2i): Int {
        return abs(x - other.x) + abs(y - other.y)
    }

    fun areaBetween(other: Vec2i): Long {
        return abs(x - other.x + 1).toLong() * abs(y - other.y + 1).toLong()
    }

    fun heuristicDistance(end: Vec2i): Int {
        val dx = abs(this.x - end.x)
        val dy = abs(this.y - end.y)
        return (dx + dy) + -2 * minOf(dx, dy)
    }

    fun toArrayIndex(rowWidth: Int): Int {
        return y * rowWidth + x
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    companion object {
        val ORIGIN = Vec2i(0, 0)
        val UP = Vec2i(0, -1)
        val DOWN = Vec2i(0, 1)
        val LEFT = Vec2i(-1, 0)
        val RIGHT = Vec2i(1, 0)

        val CARDINAL = listOf(UP, DOWN, LEFT, RIGHT)
        val DIAGONALS = listOf(UP + LEFT, UP + RIGHT, DOWN + RIGHT, DOWN + LEFT)
    }
}

data class Vec2iV<T>(val point: Vec2i, val value: T) {
    override fun toString(): String = "$value"
    fun toStringFull(): String = "$point: $value"
}

fun List<String>.toVec2iVList(): List<Vec2iV<Char>> {
    return this.flatMapIndexed { y, row -> row.mapIndexed { x, cell ->  Vec2iV(Vec2i(x, y), cell)} }
}

fun List<String>.toVec2iList(sep: String = ","): List<Vec2i> {
    return map {
        val (x, y) = it.split(sep)
        Vec2i(x.toInt(), y.toInt())
    }
}
