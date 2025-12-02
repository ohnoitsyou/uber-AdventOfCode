package dev.dayoung.adventofcode.twentynineteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component
import kotlin.math.floor

@Component
class Day201901: PuzzleSolution(2019, 1, true) {

    private fun calculateFuel(mass: Int) = (floor(mass / 3.0) - 2).toInt()

    private fun calculateFuelForFuel(mass: Int): Int {
        val newFuel = calculateFuel(mass)
        if(newFuel <= 0) return 0
        return newFuel + calculateFuelForFuel(newFuel)
    }

    private fun partOne(input: List<Int>): Int {
        return input.fold(0) { acc, v -> acc + calculateFuel(v) }
    }

    private fun partTwo(input: List<Int>): Int {
        return input.fold(0) { acc: Int, v: Int ->
            val fuel: Int = calculateFuel(v)
            val extraFuel: Int = calculateFuelForFuel(fuel)
            acc + fuel + extraFuel
        }
    }

    val parser = { input: List<String> -> input.map { it.toInt()} }

    override fun solve(sampleMode: Boolean) {
        log.info { "2019 - Day 1" }
        Utils.readInputResource("2019/1.txt", sampleMode)?.let { parser(it) }?.let { input ->
            log.info { "Part one: ${partOne(input)}" }
            log.info { "Part two: ${partTwo(input)}" }
        }
    }
}
