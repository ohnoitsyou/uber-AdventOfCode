package dev.dayoung.adventofcode.twentyfifteen

import dev.dayoung.adventofcode.Vec2i
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03Test {

    val vec2iSymbol = mapOf(Vec2i.UP to "^", Vec2i.DOWN to "v", Vec2i.LEFT to "<", Vec2i.RIGHT to ">")
    @Test
    fun `RIGHT delivers two presents`() {
        val directionList = listOf(Vec2i.RIGHT).joinToString("") { vec2iSymbol[it]!! }

        assertEquals(2, Day03().partOne(directionList))
    }

    @Test
    fun `UP RIGHT DOWN LEFT delivers 4 presents`() {
        val directionList = listOf(Vec2i.UP, Vec2i.RIGHT, Vec2i.DOWN, Vec2i.LEFT).joinToString("") { vec2iSymbol[it]!! }
        assertEquals(4, Day03().partOne(directionList))
    }
    @Test
    fun `UP DOWN UP DOWN UP DOWN UP DOWN UP DOWN delivers 2 presents`() {
        val directionList = listOf(
            Vec2i.UP, Vec2i.DOWN,
            Vec2i.UP, Vec2i.DOWN,
            Vec2i.UP, Vec2i.DOWN,
            Vec2i.UP, Vec2i.DOWN,
            Vec2i.UP, Vec2i.DOWN).joinToString("") { vec2iSymbol[it]!! }
        assertEquals(2, Day03().partOne(directionList))
    }

    @Test
    fun `Robo Santa UP DOWN delivers 3 presents`() {
        val directionList = listOf(Vec2i.UP, Vec2i.DOWN).joinToString("") { vec2iSymbol[it]!! }
        assertEquals(3, Day03().partTwo(directionList))
    }
    @Test
    fun `Robo Santa UP RIGHT DOWN LEFT delivers 3 presents`() {
        val directionList = listOf(Vec2i.UP, Vec2i.RIGHT, Vec2i.DOWN, Vec2i.LEFT).joinToString("") { vec2iSymbol[it]!! }
        assertEquals(3, Day03().partTwo(directionList))
    }

    @Test
    fun `Robo Santa UP DOWN UP DOWN UP DOWN UP DOWN UP DOWN delivers 11 presents`() {
        val directionList = listOf(
            Vec2i.UP, Vec2i.DOWN,
            Vec2i.UP, Vec2i.DOWN,
            Vec2i.UP, Vec2i.DOWN,
            Vec2i.UP, Vec2i.DOWN,
            Vec2i.UP, Vec2i.DOWN).joinToString("") { vec2iSymbol[it]!! }
        assertEquals(11, Day03().partTwo(directionList))
    }
}
