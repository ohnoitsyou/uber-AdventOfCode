package dev.dayoung.adventofcode.twentyfifteen

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03Test {

    @Test
    fun `RIGHT delivers two presents`() {
        val directionList = listOf(Day03.Direction.RIGHT)
        assertEquals(2, Day03().partOne(directionList))
    }

    @Test
    fun `UP RIGHT DOWN LEFT delivers 4 presents`() {
        val directionList = listOf(Day03.Direction.UP, Day03.Direction.RIGHT, Day03.Direction.DOWN, Day03.Direction.LEFT)
        assertEquals(4, Day03().partOne(directionList))
    }
    @Test
    fun `UP DOWN UP DOWN UP DOWN UP DOWN UP DOWN delivers 2 presents`() {
        val directionList = listOf(
            Day03.Direction.UP, Day03.Direction.DOWN,
            Day03.Direction.UP, Day03.Direction.DOWN,
            Day03.Direction.UP, Day03.Direction.DOWN,
            Day03.Direction.UP, Day03.Direction.DOWN,
            Day03.Direction.UP, Day03.Direction.DOWN)
        assertEquals(2, Day03().partOne(directionList))
    }

    @Test
    fun `Robo Santa UP DOWN delivers 3 presents`() {
        val directionList = listOf(Day03.Direction.UP, Day03.Direction.DOWN)
        assertEquals(3, Day03().partTwo(directionList))
    }
    @Test
    fun `Robo Santa UP RIGHT DOWN LEFT delivers 3 presents`() {
        val directionList = listOf(Day03.Direction.UP, Day03.Direction.RIGHT, Day03.Direction.DOWN, Day03.Direction.LEFT)
        assertEquals(3, Day03().partTwo(directionList))
    }

    @Test
    fun `Robo Santa UP DOWN UP DOWN UP DOWN UP DOWN UP DOWN delivers 11 presents`() {
        val directionList = listOf(
            Day03.Direction.UP, Day03.Direction.DOWN,
            Day03.Direction.UP, Day03.Direction.DOWN,
            Day03.Direction.UP, Day03.Direction.DOWN,
            Day03.Direction.UP, Day03.Direction.DOWN,
            Day03.Direction.UP, Day03.Direction.DOWN)
        assertEquals(11, Day03().partTwo(directionList))
    }
}