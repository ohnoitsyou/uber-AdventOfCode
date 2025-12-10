package dev.dayoung.adventofcode

import java.io.BufferedWriter
import java.io.File
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Utils {
    companion object {
        private fun readResourceFile(filename: String) =
            this::class.java.getResourceAsStream(filename)?.bufferedReader()?.readLines()

        private fun writeCacheFile(filename: String): BufferedWriter {
            val userDir = System.getProperty("user.dir")
            val cacheDir = File(userDir, "cache")
            if (!cacheDir.exists()) cacheDir.mkdirs()
            return File(cacheDir, filename).bufferedWriter()
        }

        fun readInputResource(filename: String, sampleMode: Boolean = false) =
            when (sampleMode) {
                false -> readResourceFile("/input/$filename")
                true -> readResourceFile("/sample/$filename")
            }

        fun writeCacheResource(filename: String, sampleMode: Boolean = false): BufferedWriter =
            when(sampleMode) {
                false -> writeCacheFile("/input/$filename")
                true -> writeCacheFile("/sample/$filename")
            }
    }
}

data class Vec2i(val x: Int, val y: Int) {
    operator fun plus(other: Vec2i): Vec2i { return Vec2i(x + other.x, y + other.y) }
    operator fun minus(other: Vec2i): Vec2i { return Vec2i(x - other.x, y - other.y) }

    val linearNeighbors: List<Vec2i>
        get() = CARDINAL.map { this + it}
    val diagonalNeighbors: List<Vec2i>
        get() = DIAGONALS.map { this + it }
    val allNeighbors: List<Vec2i>
        get() = CARDINAL.map { this + it } + DIAGONALS.map { this + it }

    fun manhattanDistance(other: Vec2i): Int {
        return abs(x - other.x) + abs(y - other.y)
    }

    fun heuristicDistance( end: Vec2i): Int {
        val dx = abs(this.x - end.x)
        val dy = abs(this.y - end.y)
        return (dx + dy) + -2 * minOf(dx, dy)
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

fun String.cut(delimiter: String): Pair<String, String> {
    require(this.indexOf(delimiter) >= 0) { "Delimiter ($delimiter) must be in string ($this)" }
    return this.substringBefore(delimiter) to this.substringAfter(delimiter)
}

fun List<String>.cut(delimiter: String): List<Pair<String, String>> {
    return this.map { it.cut(delimiter) }
}

fun List<String>.splitFile(predicate: (String) -> Boolean): Pair<List<String>, List<String>> {
    return this.takeWhile(predicate) to this.dropWhile(predicate)
}

fun List<String>.toIntRanges(sep: String = "-"): List<IntRange> {
    return map { IntRange(it.substringBefore(sep).toInt(), it.substringAfter(sep).toInt()) }
}

fun List<String>.toLongRanges(sep: String = "-"): List<LongRange> {
    return map { LongRange(it.substringBefore(sep).toLong(), it.substringAfter(sep).toLong()) }
}


data class Vec3i(val x: Int, val y: Int, val z: Int) {
    fun eDistance(other: Vec3i): Double {
        val dx = (x - other.x).toDouble()
        val dy = (y - other.y).toDouble()
        val dz = (z - other.z).toDouble()
        return sqrt(dx.pow(2) + dy.pow(2) + dz.pow(2))
    }
}

fun List<String>.toVec3iList(sep: String = ",") : List<Vec3i> {
    return map {
        val (x, y, z) = it.split(",")
        Vec3i(x.toInt(), y.toInt(), z.toInt())
    }
}
