package dev.dayoung.adventofcode.twentyfifteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component
import java.awt.Point
import java.awt.Rectangle

@Component
class Day06 : PuzzleSolution(2015, 6){
    companion object {
        val pointRegex = Regex("""(\d{1,3}),(\d{1,3}) through (\d{1,3}),(\d{1,3})""")
    }
    enum class Command {
        ON,
        OFF,
        TOGGLE
    }

    data class Instruction(val command: Command, val p1: Point, val p2: Point)

    abstract class Grid {
        private val height = 1000
        private val width = 1000

        val lights = IntArray(height * width) { 0 }

        fun turnOff(p1: Point, p2: Point) {
            val pointRect = Rectangle(p1)
                pointRect.add(p2)
            for (x in pointRect.x .. pointRect.x + pointRect.size.width) {
                for (y in pointRect.y .. pointRect.y + pointRect.size.height) {
                    doOff(y + (x * width))
                }
            }
        }

        abstract fun doOff(light: Int)

        fun turnOn(p1: Point, p2: Point) {
            val pointRect = Rectangle(p1)
                pointRect.add(p2)
            for (x in pointRect.x .. pointRect.x + pointRect.size.width) {
                for (y in pointRect.y .. pointRect.y + pointRect.size.height) {
                    doOn(y + (x * width))
                }
            }
        }

        abstract fun doOn(light: Int)

        fun toggle(p1: Point, p2: Point) {
            val pointRect = Rectangle(p1)
                pointRect.add(p2)
            for (x in pointRect.x .. pointRect.x + pointRect.size.width) {
                for (y in pointRect.y .. pointRect.y + pointRect.size.height) {
                    doToggle(y + (x * width))
                }
            }
        }

        abstract fun doToggle(light: Int)

        fun litCount(): Int = lights.count { it == 1 }

        fun brightness(): Int = lights.sum()
    }

    class Grid1 : Grid() {
        override fun doOff(light: Int) { lights[light] = 0 }
        override fun doOn(light: Int) { lights[light] = 1 }
        override fun doToggle(light: Int) { lights[light] = if(lights[light] == 1) 0 else 1 }
    }

    class Grid2 : Grid() {
        override fun doOff(light: Int) { lights[light] = if(lights[light] == 0) 0 else lights[light] - 1 }
        override fun doOn(light: Int) { lights[light] += 1 }
        override fun doToggle(light: Int) { lights[light] += 2 }
    }

    fun partOne(instructions: List<Instruction>): Int {
        val grid = Grid1()
        instructions.forEach { instruction ->
            when(instruction.command) {
                Command.ON -> grid.turnOn(instruction.p1, instruction.p2)
                Command.OFF -> grid.turnOff(instruction.p1, instruction.p2)
                Command.TOGGLE -> grid.toggle(instruction.p1, instruction.p2)
            }
        }
        return grid.litCount()
    }

    fun partTwo(instructions: List<Instruction>): Int {
        val grid = Grid2()
        instructions.forEach { instruction ->
            when(instruction.command) {
                Command.ON -> grid.turnOn(instruction.p1, instruction.p2)
                Command.OFF -> grid.turnOff(instruction.p1, instruction.p2)
                Command.TOGGLE -> grid.toggle(instruction.p1, instruction.p2)
            }
        }
        return grid.brightness()
    }
    override fun solve(sampleMode: Boolean) {
        println("2015 Day 06")
        Utils.readInputResource(sampleMode, "2015/six.txt.txt")?.let {
            val instructions = it.toGridInstructions()
            println("Part One: ${partOne(instructions)}")
            println("Part Two: ${partTwo(instructions)}")
        }
    }
}

fun List<String>.toGridInstructions(): List<Day06.Instruction> {
    return mapNotNull { line ->
        val command = when {
            line.startsWith("turn off") -> Day06.Command.OFF
            line.startsWith("turn on") -> Day06.Command.ON
            line.startsWith("toggle") -> Day06.Command.TOGGLE
            else -> null
        }

        val points = Day06.pointRegex.find(line)?.groups?.drop(1)?.chunked(2)

        val p1 = Point(points!![0][0]!!.value.toInt(), points[0][1]!!.value.toInt())
        val p2 = Point(points[1][0]!!.value.toInt(), points[1][1]!!.value.toInt())

        if(command != null) Day06.Instruction(command, p1, p2) else null
    }
}
