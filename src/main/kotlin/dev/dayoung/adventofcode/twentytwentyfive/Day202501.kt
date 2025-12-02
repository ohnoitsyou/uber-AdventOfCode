package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import kotlin.math.abs

// Huge help from https://github.com/nbanman/pdx-puzzles/blob/main/kotlin/advent/src/main/kotlin/org/gristle/pdxpuzzles/advent/y2025/Y25D01.kt
// Learned there is a difference between `%` and `mod` -> it makes alllll the difference with this one
// Changed out the methodology just slightly, but the idea is there
class Day202501: PuzzleSolution(2025, 1) {

    fun partOne(input: List<Int>): Int {
        return input.runningFold(50) { dial, value ->
            (dial + value).mod(100)
        }.count { it == 0 }
    }

    fun partTwo(input: List<Int>): Int {
        return input.fold(Pair(50, 0)) { acc, oper ->
            var (value, clicks) = acc
            value += oper
            if (value <= 0 && oper != value) {
                clicks++
            }
            value.mod(100) to clicks + abs(value) / 100
        }.second
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource(sampleMode, "2025/01.txt")?.map {
            it.substring(1).toInt() * if(it[0] == 'L') -1 else 1
        }?.let { input ->
            println("Zero count: ${partOne(input)}")
            println("Click count: ${partTwo(input)}")
        }
    }
}
