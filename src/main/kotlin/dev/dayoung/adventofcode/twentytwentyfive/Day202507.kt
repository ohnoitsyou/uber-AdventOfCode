package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.structures.MutableGrid

class Day202507: PuzzleSolution(2025, 7) {
    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2025/07.txt", sampleMode)?.let { input ->
            val points = input.joinToString("").toMutableList()
            val grid = MutableGrid(points, input.first().length, input.size)
        }
    }
}

fun main() = Day202507().solve(true)
