package dev.dayoung.adventofcode.twentytwentythree

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component
import kotlin.math.abs

@Component
class Day202306 : PuzzleSolution(2023, 6, true) {
    fun List<Int>.product() = this.fold(1) { acc, value -> acc * value }
    fun distance(speed: Long, time: Long) = speed * time
    fun distance(speed:Int, time: Int) = speed * time

    fun partOne(times: List<Int>, distances: List<Int>): Int {
        return times.zip(distances).map { (raceTime, raceDistance) ->
            (0 .. raceTime).map {
                val moveTime = abs(it - raceTime)
                distance(it, moveTime)
            }.count { it > raceDistance}
        }.product()
    }

    fun partTwo(times: List<Int>, distances: List<Int>): Int {
        val time = times.joinToString("").toLong()
        val record = distances.joinToString("").toLong()
        // Calculate the minimum hold time required to beat the record
        val shortest = (0 .. time).first { holdTime ->
            val moveTime = abs(holdTime - time)
            distance(holdTime, moveTime) > record
        }
        // Extra 1 to account for the final time the record would be broken
        return (time - (shortest * 2) + 1).toInt()
    }

    override fun solve(sampleMode: Boolean) {
        println("2023 Day 06")
        Utils.readInputResource("2023/six.txt.txt", sampleMode)?.let {
            val times = it.first().substringAfter(":").trim().split(" +".toRegex()).map { time ->
                time.toInt()
            }
            val distances = it.last().substringAfter(":").trim().split(" +".toRegex()).map { distance ->
                distance.toInt()
            }
            println("Part One: ${partOne(times, distances)}")
            println("Part Two: ${partTwo(times, distances)}")
        }
    }
}
