package dev.dayoung.adventofcode.twentyfifteen

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestFactory

class Day05Test {

    @Test
    fun `Test ugknbfddgicrmopn`() {
        val inputs = listOf("ugknbfddgicrmopn")
        assertEquals(1, Day05().partOne(inputs))
    }

    @Test
    fun `Test aaa`() {
        val inputs = listOf("aaa")
        assertEquals(1, Day05().partOne(inputs))
    }

    @Test
    fun `Test jchzalrnumimnmhp`() {
        val inputs = listOf("jchzalrnumimnmhp")
        assertEquals(0, Day05().partOne(inputs))
    }

    @Test
    fun `Test haegwjzuvuyypxyu`() {
        val inputs = listOf("haegwjzuvuyypxyu")
        assertEquals(0, Day05().partOne(inputs))
    }

    @Test
    fun `Test dvszwmarrgswjxmb`() {
        val inputs = listOf("dvszwmarrgswjxmb")
        assertEquals(0, Day05().partOne(inputs))
    }

    @Test
    fun `Test qjhvhtzxzqqjkmpb`() {
        val inputs = listOf("qjhvhtzxzqqjkmpb")
        assertEquals(1, Day05().partTwo(inputs))
    }
    @Test
    fun `Test xxyxx`() {
        val inputs = listOf("xxyxx")
        assertEquals(1, Day05().partTwo(inputs))
    }

    @Test
    fun `Test uurcxstgmygtbstg`() {
        val inputs = listOf("uurcxstgmygtbstg")
        assertEquals(0, Day05().partTwo(inputs))
    }

    @Test
    fun `Test ieodomkazucvgmuy`() {
        val inputs = listOf("ieodomkazucvgmuy")
        assertEquals(0, Day05().partTwo(inputs))
    }
    @Test
    fun `Test all`() {
        val inputs = listOf("qjhvhtzxzqqjkmpb", "xxyxx", "uurcxstgmygtbstg", "ieodomkazucvgmuy")
        assertEquals(2, Day05().partTwo(inputs))
    }
}