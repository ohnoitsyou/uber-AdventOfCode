package dev.dayoung.adventofcode.twentyfifteen

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02Test {
    @Test
    fun `2x3x4 requires 58 feet of paper`() {
        assertEquals(58, Day02().partOne(listOf(Day02.lineToDimensions("2x3x4"))))
    }

    @Test
    fun `1x1x10 requires  43 feet of paper`() {
        assertEquals(43, Day02().partOne(listOf(Day02.lineToDimensions("1x1x10"))))
    }

    @Test
    fun `2x3x4 requires 34 feet of ribbon`() {
        assertEquals(34, Day02().partTwo(listOf(Day02.lineToDimensions("2x3x4"))))
    }

    @Test
    fun `1x1x10 requires 14 feet of ribbon`() {
        assertEquals(14, Day02().partTwo(listOf(Day02.lineToDimensions("1x1x10"))))
    }
}