package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component
import java.awt.Point

@Component
class Day201601: PuzzleSolution(2016, 1) {

    class Compass(var facing: Direction = Direction.NORTH) {
        enum class Direction { NORTH, SOUTH, EAST, WEST }
        enum class Rotate { CW, CCW }
        fun rotateLeft() = rotate(Rotate.CCW)
        fun rotateRight() = rotate(Rotate.CW)
        private fun rotate(direction: Rotate) {
            facing = when(facing) {
                Direction.NORTH -> if(direction == Rotate.CW) Direction.EAST else Direction.WEST
                Direction.SOUTH -> if(direction == Rotate.CW) Direction.WEST else Direction.EAST
                Direction.EAST -> if(direction == Rotate.CW) Direction.SOUTH else Direction.NORTH
                Direction.WEST -> if(direction == Rotate.CW) Direction.NORTH else Direction.SOUTH
            }
        }
        fun point(direction: Direction) {
            facing = direction
        }
    }

    fun move(direction: Compass, count: Int, pos: Point) {
        when(direction.facing) {
            Compass.Direction.NORTH -> pos.translate(count, 0)
            Compass.Direction.SOUTH -> pos.translate(-count, 0)
            Compass.Direction.EAST -> pos.translate(0, count)
            Compass.Direction.WEST -> pos.translate(0, -count)
        }
    }

    fun partOne(input: List<String>): Int {
        val location = Point(0, 0)
        val compass = Compass()
        input.forEach {
//            println("Direction: $it")
            val (lr, count) = it.toList().take(2).map { it.toString() }
            when (lr) {
                "L" -> compass.rotateLeft()
                "R" -> compass.rotateRight()
                else -> {}
            }
            move(compass, count.toInt(), location)
        }
        return location.x + location.y
    }
    override fun solve(sampleMode: Boolean) {
        println("2016 Day 01")
        Utils.readInputResource(sampleMode, "2016/one.txt")?.first()?.let {
            val instructions = it.split(", ")
            println("Part One: ${partOne(instructions)}")
            println("Part Two")
        }
    }
}
