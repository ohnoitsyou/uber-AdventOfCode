package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.Vec3i
import dev.dayoung.adventofcode.toVec3iList

class Day202508 : PuzzleSolution(2025, 8) {
    val junctionCache = mutableMapOf<Pair<Vec3i, Vec3i>, Double>()
    val jCache = mutableListOf<Triple<Vec3i, Vec3i, Double>>()
    val circuits = mutableListOf<MutableSet<Vec3i>>()
    fun partOne(maxConnections: Int): Int  {
        circuits.size.logit("Circuit count")
        var matches = 0
        while(matches < maxConnections - 1) {
            val minPair = junctionCache.entries.minBy { it.value }.logit("[$matches] Min vector distance")
            junctionCache.remove(minPair.key)
            // does anything already contain the first
            val firstContained = circuits.firstOrNull { it.contains(minPair.key.first)}
            // does anything already contain the second
            val secondContained = circuits.firstOrNull { it.contains(minPair.key.second) }
            // found first, found second, they weren't found in the same
            if(!firstContained.isNullOrEmpty() && !secondContained.isNullOrEmpty() && firstContained != secondContained) {
                // Copy the second into the first
                log.info { "# Joining two circuits $secondContained into $firstContained" }
                firstContained.addAll(secondContained).logit("Add joined")
                circuits.remove(secondContained).logit("Remove second")
                firstContained.logit("Joined")
                matches++
            } else if(!circuits.any { it.contains(minPair.key.first) && it.contains(minPair.key.second) }) {
                val existingC = circuits.firstOrNull { it.contains(minPair.key.first) || it.contains(minPair.key.second) }
                if(existingC != null) {
                    log.info { "# Adding points to existing circuit $existingC -> ${minPair.key}" }
                    existingC.add(minPair.key.first)
                    existingC.add(minPair.key.second)
                    existingC.logit("Existing")
                    circuits.logit("All circuits")
                } else {
                    log.info { "# Crating new circuit ${minPair.key}" }
                    circuits.add(mutableSetOf(minPair.key.first, minPair.key.second))
                    circuits.logit("All circuits")
                }
                matches++
            } else {
                log.info { "## Circuit already made: ${minPair.key}" }
            }
            log.info { "" }
        }

        junctionCache.size.logit("Remaining junctions")

        return 0
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2025/08.txt", sampleMode)?.let { input ->
            val vectors = input.toVec3iList()
            vectors.forEachIndexed { i, iVector ->
                vectors.subList(i + 1, vectors.size).forEach { jVector ->
                    val d = iVector.eDistance(jVector)//.logit("$iVector -> $jVector")
                    junctionCache[iVector to jVector] = d
                    jCache.add(Triple(iVector, jVector, d))
                }
            }
            jCache.sortBy { it.third }
            jCache.take(1000)
            circuits.addAll(vectors.map { mutableSetOf(it) })
//            val minPair = junctionCache.entries.minBy { it.value }.logit("Min vector distance")
//            junctionCache.remove(minPair.key)
//            circuits.firstOrNull { it.contains(minPair.key.first) || it.contains(minPair.key.second) }?.let {
//                it.add(minPair.key.first)
//                it.add(minPair.key.second)
//            } ?: circuits.add(mutableSetOf(minPair.key.first, minPair.key.second))
//            val secondPair = junctionCache.entries.minBy { it.value }.logit("Second Min vector distance")
//            junctionCache.remove(secondPair.key)
//            circuits.firstOrNull { it.contains(secondPair.key.first) || it.contains(secondPair.key.second) }?.let {
//                it.add(secondPair.key.first)
//                it.add(secondPair.key.second)
//            } ?: circuits.add(mutableSetOf(secondPair.key.first, secondPair.key.second))
            partOne(10)
            circuits.map { it.size }.logit("Circuits")
            circuits.sortedByDescending { it.size }.take(3).map { it.size }.fold(1L) { acc, i -> acc * i }.logit("Final answer")
//            junctionCache.logit("JunctionCache")
        }
    }
}

fun main() = Day202508().solve(true)
