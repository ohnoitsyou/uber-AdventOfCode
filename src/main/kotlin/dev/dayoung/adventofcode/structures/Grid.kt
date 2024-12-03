package dev.dayoung.adventofcode.structures

class Grid<T>(private val height: Int, private val width: Int) {
    private val cells = mutableListOf<T>()

    fun setCell(x: Int, y: Int, value: T) {
        cells[x*height + y*width] = value
    }

    fun getCell(x: Int, y: Int): T {
        return cells[x*height + y*width]
    }
}
