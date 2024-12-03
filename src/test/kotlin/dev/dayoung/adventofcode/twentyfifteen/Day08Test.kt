package dev.dayoung.adventofcode.twentyfifteen

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day08Test {
    @Test
    fun `Test Double Quote`() {
        val input = listOf("\"\"")
        val results = Day08().partOne(input)
        println(results)
    }

    @Test
    fun `Test abc`() {
        val input = listOf("\"abc\"")
        val results = Day08().partOne(input)
        println(results)
    }

    @Test
    fun `Test 'aaa'aaa'`() {
        val input = listOf("\"aaa\\\"aaa\"")
        val results = Day08().partOne(input)
        println(results)
    }

    @Test
    fun `Test Hex`() {
        val input = listOf("\"\\x27\"")
        val results = Day08().partOne(input)
        println(results)
    }
}