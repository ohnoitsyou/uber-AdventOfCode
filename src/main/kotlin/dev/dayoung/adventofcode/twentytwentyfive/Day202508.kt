package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.Vec3i
import dev.dayoung.adventofcode.toVec3iList
import org.springframework.stereotype.Component

typealias JBox = Vec3i
typealias JBoxPair = Triple<JBox, JBox, Double>
typealias JBoxPairList = MutableList<JBoxPair>
typealias Circuit = MutableSet<JBox>
typealias CircuitList = MutableList<Circuit>

@Component
class Day202508 : PuzzleSolution(PUZZLE_YEAR, PUZZLE_DAY) {

    fun partOne(jBoxes: List<JBox>, maxConnections: Int): Int {
        val (circuits, jBoxPairs) = buildCircuitsAndPairs(jBoxes)
        var connections = 0
        // Not entirely sure why this need to have the `- 1`, but it does one extra connection without it (obviously)
        while(connections < maxConnections - 1) {
            val current: JBoxPair = jBoxPairs.removeFirst()
            connectCircuits(current, circuits)
            connections++
        }

        return circuits.sortedByDescending { it.size }.take(3).fold(1) { acc, i -> acc * i.size }
    }

    fun partTwo(jBoxes: List<JBox>): Int {
        val (circuits, boxPairs) = buildCircuitsAndPairs(jBoxes)
        var connections = 0
        var current: JBoxPair
        do {
            current = boxPairs.removeFirst()
            connectCircuits(current, circuits)
            connections++
        } while (circuits.size > 1)
        log.info { "All circuits connected in $connections steps" }
        return current.first.x * current.second.x
    }

    fun connectCircuits(current: JBoxPair, circuits: CircuitList) {
        val first: Circuit = circuits.first { it.contains(current.first)}
        val second: Circuit= circuits.first { it.contains(current.second) }
        if(first != second) {
            // combine the two circuits, copy the second into the first
            first.addAll(second)
            // remove the second circuit
            circuits.remove(second)
        }
    }

    fun buildCircuitsAndPairs(jBoxes: List<Vec3i>): Pair<CircuitList, JBoxPairList> {
        val circuits= jBoxes.map { mutableSetOf(it) }.toMutableList()
        val boxPairs= jBoxes.flatMapIndexed { i, iJBox ->
            jBoxes.subList(i + 1, jBoxes.size).map { jJBox ->
                Triple(iJBox, jJBox, iJBox.eDistance(jJBox))
            }
        }.sortedBy { it.third }.toMutableList()
        return circuits to boxPairs
    }

    override fun solve(sampleMode: Boolean) {
        val connectionCount = if(sampleMode) SAMPLE_MAX_CONNECTIONS else FULL_MAX_CONNECTIONS
        Utils.readInputResource("2025/08.txt", sampleMode)?.let { input ->
            val vectors = input.toVec3iList()
            partOne(vectors, connectionCount).logit("Product of largest 3 circuits after $connectionCount steps")
            partTwo(vectors).logit("Product of x pos of last connected circuit")
        }
    }
    companion object {
        const val SAMPLE_MAX_CONNECTIONS = 10
        const val FULL_MAX_CONNECTIONS = 1000
        const val PUZZLE_YEAR = 2025
        const val PUZZLE_DAY = 8
    }
}

fun main() = Day202508().solve(false)
