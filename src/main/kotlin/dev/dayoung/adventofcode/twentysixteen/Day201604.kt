package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component

@Component
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
    private fun partOne(input: List<Room>): List<Room> {
        return input.filter { room -> room.isValid() }
    }

    private fun partTwo(input: List<Room>, targetString: String): Room {
        return input.first { room ->
            val rot = room.sectorId % 26
            room.name.map { c ->
                if(c == '-') { ' ' } else {
                    (((c.code - 'a'.code + rot) % 26) + 'a'.code).toChar()
                }
            }.joinToString("").contains(targetString)
        }
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource(sampleMode, "2016/four.txt")?.let { lines ->
            val roomInput = lines.mapNotNull { line ->
                lineRegex.find(line)?.groupValues?.let { match ->
                    Room(match[1], match[2].toInt(10), match[3])
                }
            }
            val validRooms = partOne(roomInput)
            println("Sector ID sum: ${validRooms.sumOf { it.sectorId }}")
            println("North-pole storage: ${partTwo(validRooms, "northpole").sectorId}")
        }
    }
}
