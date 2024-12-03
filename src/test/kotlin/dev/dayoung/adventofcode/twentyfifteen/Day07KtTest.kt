package dev.dayoung.adventofcode.twentyfifteen

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day07KtTest {
    val simpleCiruit = listOf("123 -> x", "456 -> y", "x AND y -> d", "x OR y -> e",
            "x LSHIFT 2 -> f", "y RSHIFT 2 -> g", "NOT x -> h", "NOT y -> i")

    @Test
    fun findSimpleOrigins() {
        simpleCiruit.toCircuitInstructions()
    }
}