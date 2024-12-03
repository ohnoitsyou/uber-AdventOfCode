package dev.dayoung.adventofcode.twentyfifteen

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day06Test {
    @Test
    fun `parse instructions`() {
        val input = listOf("turn on 0,0 through 999,999")
        val instructions = input.toGridInstructions()
        println(instructions)
    }

    @Test
    fun `turn on 0,0 through 999,999`() {
        val input = listOf("turn on 0,0 through 999,999")
        val instructions = input.toGridInstructions()

        assertEquals(1_000_000, Day06().partOne(instructions))
    }

    @Test
    fun `toggle 0,0 through 999,0`() {
        val input = listOf("toggle 0,0 through 999,0")
        val instructions = input.toGridInstructions()

        assertEquals(1_000, Day06().partOne(instructions))
    }

    @Test
    fun `turn off 499,499 through 500,500`() {
        val input = listOf("turn on 0,0 through 999,999", "turn off 499,499 through 500,500")
        val instructions = input.toGridInstructions()

        assertEquals(1_000_000 - 4, Day06().partOne(instructions))

    }

    @Test
    fun `turn on 0,0 through 0,0`() {
        val input = listOf("turn on 0,0 through 0,0")
        val instructions = input.toGridInstructions()

        assertEquals(1, Day06().partTwo(instructions))
    }

    @Test
    fun `toggle 0,0 through 999,999`() {
        val input = listOf("toggle 0,0 through 999,999")
        val instructions = input.toGridInstructions()

        assertEquals(2_000_000, Day06().partTwo(instructions))
    }
}