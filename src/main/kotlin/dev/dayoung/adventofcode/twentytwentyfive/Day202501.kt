package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import kotlin.math.abs

class Day202501: PuzzleSolution(2025, 1) {

    object Dial {
        var position = 50
        var zeroStops = 0

        fun reset() {
            position = 50
            zeroStops = 0
        }

        fun left(value: Int) {
            position -= value % 100
            when {
                position == 0 || position == 100 -> {
                    zeroStops++
                    position = 0
                }
                position < 0 -> position = (position + 100) % 100
            }
        }

        fun right(value: Int) {
            position += value % 100
            when {
                position == 0 || position == 100 -> {
                    zeroStops++
                    position = 0
                }
                position > 99 -> position = (position - 100) % 100
            }
        }
    }

    object Dial2 {
        var position = 50
        var zeroStops = 0

        fun reset() {
            position = 50
            zeroStops = 0
        }

        fun left(value: Int) {
            val absolutePosition = position - value
            val oldzsp = zeroStops
            if(position == 0 && absolutePosition % 100 == 0) {
                zeroStops += (value / 100) // Landing on zero still counts
            } else if(absolutePosition <= 0) {
                val stops = maxOf(1, abs(absolutePosition / 100) + 1)
//                println("Stops: $stops")
                zeroStops += stops
                val nv = 100 - abs(absolutePosition % 100)
                position =  if(nv == 100) 0 else nv
            } else {
                position = abs(absolutePosition)
            }
            print(position)
            if(oldzsp != zeroStops) {
                print("; during this rotation, it points at 0 ${zeroStops - oldzsp} times")
            }
        }

        fun right(value: Int) {
            val absolutePosition = position + value
            val oldzsp = zeroStops
            if(absolutePosition % 100 == 0) {
                zeroStops++  // landing on zero still counts
            } else if(absolutePosition >= 100) {
                val stops = maxOf(1, abs(absolutePosition) / 100)
//                println("Stops: $stops")
                zeroStops += stops
                position = absolutePosition % 100
            } else {
                position = absolutePosition
            }
            print(position)
            if(oldzsp != zeroStops) {
                print("; during this rotation, it points at 0 ${zeroStops - oldzsp} times")
            }
        }
    }

    val dial = Dial
    val dial2 = Dial2

    fun partOne(input: List<String>): Int {
        val checkpoints = listOf(Pair(-68, 82), Pair(-30, 52), Pair(48, 0), Pair(-5, 95), Pair(60, 55), Pair(-55, 0), Pair(-1, 99), Pair(-99, 0), Pair(14, 14), Pair(-82, 32))
        input.forEachIndexed { idx, line ->
            when(line.first()) {
                'L' -> dial.left(line.substring(1).toInt())
                'R' -> dial.right(line.substring(1).toInt())
            }
            println()
        }
        return dial.zeroStops
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource(sampleMode, "2025/01.txt")?.let { input ->
            println("Zero count: ${partOne(input)}")
        }
    }
}

fun main() {
    Day202501().solve(false)
}

//        fun right(value: Int) {
//            println("$position + $value = ${position + value}")
//
//            val absPos = position + value
//            println("absolute: $absPos")
//            position = absPos % 100
//            println("Right rotate result:     $position")
//            when {
//                position == 0 || position == 100-> {
//                    zeroStops++
//                    position = 0
//                }
//                position > 99 -> {
//                    val loops = maxOf(1, abs(absPos) / 100)
//                    println("looped $loops")
//                    zeroStops += loops
//                    position = (position - 100) % 100
//                }
//            }
//            println("Right rotate correction: $position")
//        }
