package dev.dayoung.adventofcode.structures

class Grid<T>(private val points: List<T>, val width: Int, val height: Int) {
    init {
        require(points.size == width * height) { "Number of points doesn't match given height and width" }
    }

    val coords = (0 until height).flatMap { y -> (0 until width) }
}
