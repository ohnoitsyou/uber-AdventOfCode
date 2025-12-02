package dev.dayoung.adventofcode.twentynineteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.Vec2i
import org.springframework.stereotype.Component

typealias Wire = Set<Vec2i>
@Component
class Day201903: PuzzleSolution(2019,3, true) {

    // Adapt the Vec2i directions to a middle origin grid
    val singleCardinal = mapOf("U" to Vec2i.DOWN, "D" to Vec2i.UP, "L" to Vec2i.LEFT, "R" to Vec2i.RIGHT)

    val origin = Vec2i(0, 0)

    fun processWire(input: List<Pair<String, Int>>): List<Vec2i> {
        val visited = mutableListOf(origin)
        for(instr in input) {
            repeat(instr.second) {
                visited.add(visited.last() + singleCardinal[instr.first]!!)
            }
        }
        return visited.toList()
    }

    fun partOne(input: Pair<Wire, Wire>): Int {
        val wire1 = input.first
        val wire2 = input.second
        val intersections = wire1.intersect(wire2)
        return intersections.minOf { intersection ->
            origin.manhattanDistance(intersection)
        }
    }

    fun partTwo(input: Pair<Wire, Wire>): Int {
        val wire1 = input.first
        val wire2 = input.second
        val intersections = wire1.intersect(wire2)
        return intersections.minOf { intersection ->
            // Origin point counts, so tack it on at the end
            wire1.indexOfFirst { it == intersection } + wire2.indexOfFirst { it == intersection } + 2
        }
    }


    val parser = { input: List<String> ->
        val wires = input.map {
            it.split(",".toRegex()).map { instr ->
                instr.take(1) to instr.substring(1).toInt()
            }
        }
        // Drop the origin point, convert to set
        processWire(wires.first()).drop(1).toSet() to processWire(wires.last()).drop(1).toSet()
    }

    override fun solve(sampleMode: Boolean) {
        log.info { "${super.year} - Day ${super.day}" }
        Utils.readInputResource("${super.year}/${super.day}.txt", sampleMode)?.let { parser(it) }?.let { input ->
            log.info { "Part one: ${partOne(input)}" }
            log.info { "Part two: ${partTwo(input)}" }
        }
    }
}
