package dev.dayoung.adventofcode.twentyfifteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component


@Component
class Day03 : PuzzleSolution(2015, 3){

    enum class Direction{
        NONE,
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    override fun solve(sampleMode: Boolean) {
        println("2015 Day 03")
        Utils.readInputResource(sampleMode, "2015/three.txt")?.first()?.map { c ->
            when("$c") {
                ">" -> Direction.RIGHT
                "<" -> Direction.LEFT
                "^" -> Direction.UP
                "v" -> Direction.DOWN
                else -> Direction.NONE
            }
        }?.let { directionList ->
            println("Part One: ${partOne(directionList)}")
            println("Part Two: ${partTwo(directionList)}")
        }
    }

    fun moveSanta(last: Pair<Int, Int>, direction: Direction): Pair<Int, Int> =
        when(direction) {
            Direction.UP -> Pair(last.first + 1, last.second)
            Direction.DOWN -> Pair(last.first - 1, last.second)
            Direction.LEFT -> Pair(last.first, last.second - 1)
            Direction.RIGHT -> Pair(last.first, last.second + 1)
            Direction.NONE -> last
        }

    fun partOne(directionList: List<Direction>): Int {
        val houses = mutableSetOf(Pair(0, 0))
        var last = houses.last()
        directionList.forEach { direction ->
            val new = moveSanta(last, direction)
            houses.add(new)
            last = new
        }
        return houses.size
    }

    fun partTwo(directionList: List<Direction>): Int {

        var santa = true
        val houses = mutableSetOf(Pair(0, 0))
        var santaLast = houses.first()
        var robotLast = houses.first()
        directionList.forEach { direction ->
            // Move
            val last = if(santa) santaLast else robotLast
            val new = moveSanta(last, direction)
            houses.add(new)
            // Register move
            if(santa) { santaLast = new } else { robotLast = new }
            santa = !santa
        }
        return houses.size
    }
}