package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component

typealias FreqMap = List<Map<Char, Int>>

@Component
class Day201606 : PuzzleSolution(2016, 6)  {
    fun freqMap(input: List<String>): FreqMap =
        (0 until input.first().length)
            .map { colIndex ->
                input.map { rowString ->
                    rowString[colIndex]
                }.joinToString("")
            }.map { line ->
                line.groupBy { it }.mapValues { it.value.size }
            }

    fun partOne(input: FreqMap): String {
        return input.map { fMap -> fMap.maxBy { it.value }.key }.joinToString("")
    }
    fun partTwo(input: FreqMap): String {
        return input.map { fMap -> fMap.minBy { it.value }.key }.joinToString("")
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2016/six.txt", sampleMode)?.let { lines ->
            val freqMap = freqMap(lines)
            println("Part one: ${partOne(freqMap)}")
            println("Part two: ${partTwo(freqMap)}")
        }
    }
}
