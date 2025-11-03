package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils

class Day201604 : PuzzleSolution(2016, 4) {

    // Rules:
    // Each room consists of an
    // encrypted name (lowercase letters separated by dashes) followed by a dash,
    // a sector ID,
    // and a checksum in square brackets.

    // A room is real (not a decoy) if the checksum is
    // the five most common letters in the encrypted name,
    // in order, with ties broken by alphabetization.

    val lineRegex = "([a-z-]+)-([0-9]+)\\[([a-z]+)]".toRegex()

    private data class Room(val name: String, val sectorId: Int, val checksum: String) {
        fun isValid(): Boolean {
            return name.replace("-", "")
                .groupBy { it }
                .mapValues { it.value.size }
                .toList()
                .sortedWith(compareBy({0 - it.second }, { it.first })) // Sort second (count) desc, first (letter) asc
                .take(5)
                .map { it.first }
                .joinToString("") == checksum
        }
    }
    private fun partOne(input: List<String>): Int {
        val roomInput = input.mapNotNull { line ->
            lineRegex.find(line)?.groupValues?.let {
                Room(it[1], it[2].toInt(10), it[3])
            }
        }
        val validRooms = roomInput.filter { room -> room.isValid() }
        return validRooms.sumOf { it.sectorId }
    }

    private fun partTwo(input: String) {

    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource(sampleMode, "2016/four.txt")?.let {
            println("Sector ID sum: ${partOne(it)}")
        }
    }
}

fun main() {
    Day201604().solve(false)
}
