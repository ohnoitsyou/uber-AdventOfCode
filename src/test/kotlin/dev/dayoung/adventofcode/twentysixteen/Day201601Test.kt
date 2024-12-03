package dev.dayoung.adventofcode.twentysixteen

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day201601Test {

    @Test
    fun `R5, L5`() {
        val instructions = listOf("R5", "L5")
        val blocks = Day201601().partOne(instructions)
        assertEquals(10, blocks)
    }

    @Test
    fun `R2, L3`() {
        val instructions = listOf("R2", "L3")
        val blocks = Day201601().partOne(instructions)
        assertEquals(5, blocks)
    }

    @Test
    fun `R2, R2, R2`() {
        val instructions = listOf("R2", "R2", "R2")
        val blocks = Day201601().partOne(instructions)
        assertEquals(2, blocks)
    }

    @Test
    fun `R5, L5, R5, R3`() {
        val instructions = listOf("R5", "L5", "R5", "R3")
        val blocks = Day201601().partOne(instructions)
        assertEquals(12, blocks)
    }
}