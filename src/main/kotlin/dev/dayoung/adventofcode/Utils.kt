package dev.dayoung.adventofcode

class Utils {
    companion object {
        private fun readResourceFile(filename: String) =
            this::class.java.getResourceAsStream(filename)?.bufferedReader()?.readLines()

        fun readInputResource(sampleMode: Boolean = false, filename: String) =
            when (sampleMode) {
                false -> readResourceFile("/input/$filename")
                true -> readResourceFile("/sample/$filename")
            }
    }
}

data class Vec2i(val x: Int, val y: Int) {
    operator fun plus(other: Vec2i): Vec2i { return Vec2i(x + other.x, y + other.y) }
    operator fun minus(other: Vec2i): Vec2i { return Vec2i(x - other.x, y - other.y) }

    companion object {
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
