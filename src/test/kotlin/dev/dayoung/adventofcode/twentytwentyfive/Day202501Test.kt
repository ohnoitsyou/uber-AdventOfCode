package dev.dayoung.adventofcode.twentytwentyfive

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.math.abs

class Day202501Test {

    @BeforeEach
    fun resetDials() {
        println("resetting dials")
        Day202501.Dial.reset()
        Day202501.Dial2.reset()
    }

    @Test
    fun `D1 - Simple Positive rotation`() {
        val dial = Day202501.Dial
        dial.right(10)
        assert(dial.position == 60) { "Position failed: ${dial.position} != 60" }
        assert(dial.zeroStops == 0) { "Zero stops failed: ${dial.zeroStops} != 0" }
    }

    @Test
    fun `D1 - Simple negative rotation`() {
        val dial = Day202501.Dial
        dial.left(10)
        assert(dial.position == 40) { "Position failed: ${dial.position} != 40" }
        assert(dial.zeroStops == 0) { "Zero stops failed: ${dial.zeroStops} != 0" }
    }

    @Test
    fun `D1 - Simple negative zero stop rotation`() {
        val dial = Day202501.Dial
        dial.left(50)
        assert(dial.position == 0) { "Position failed: ${dial.position} != 0" }
        assert(dial.zeroStops == 1) { "Zero stops failed: ${dial.zeroStops} != 1" }
    }

    @Test
    fun `D1 - Simple positive zero stop rotation`() {
        val dial = Day202501.Dial
        dial.right(50)
        assert(dial.position == 0) { "Position failed: ${dial.position} != 0" }
        assert(dial.zeroStops == 1) { "Zero stops failed: ${dial.zeroStops} != 1" }
    }

    @Test
    fun `D1 - Simple negative loop`() {
        val dial = Day202501.Dial
        dial.left(51)
        assert(dial.position == 99) { "Position failed: ${dial.position} != 99" }
        assert(dial.zeroStops == 0) { "Zero stops failed: ${dial.zeroStops} != 0" }
    }

    @Test
    fun `D1 - Simple positive loop`() {
        val dial = Day202501.Dial
        dial.right(51)
        assert(dial.position == 1) { "Position failed: ${dial.position} != 1" }
        assert(dial.zeroStops == 0) { "Zero stops failed: ${dial.zeroStops} != 0" }
    }

    @Test
    fun `D2 - simple positive rotation`() {
        val dial = Day202501.Dial2
        dial.right(10)
        assert(dial.position == 60) { "Position failed: ${dial.position} != 60" }
        assert(dial.zeroStops == 0) { "Zero stops failed: ${dial.zeroStops} != 0" }
    }

    @Test
    fun `D2 - simple negative rotation`() {
        val dial = Day202501.Dial2
        dial.left(10)
        assert(dial.position == 40) { "Position failed: ${dial.position} != 40" }
        assert(dial.zeroStops == 0) { "Zero stops failed: ${dial.zeroStops} != 0" }
    }

    @Test
    fun `D2 - simple positive loop`() {
        val dial = Day202501.Dial2
        dial.right(51)
        assert(dial.position == 1) { "Position failed: ${dial.position} != 1" }
        assert(dial.zeroStops == 1) { "Zero stops failed: ${dial.zeroStops} != 1" }
    }

    @Test
    fun `D2 - simple negative loop`() {
        val dial = Day202501.Dial2
        dial.left(51)
        assert(dial.position == 1) { "Position failed: ${dial.position} != 1" }
        assert(dial.zeroStops == 1) { "Zero stops failed: ${dial.zeroStops} != 1" }
    }

    @Test
    fun `D2 - simple positive rotation stop`() {
        val dial = Day202501.Dial2
        dial.right(50)
        assert(dial.position == 0) { "Position failed: ${dial.position} != 0" }
        assert(dial.zeroStops == 1) { "Zero stops failed: ${dial.zeroStops} != 1" }
    }

    @Test
    fun `D2 - simple negative rotation stop`() {
        val dial = Day202501.Dial2
        dial.left(50)
        assert(dial.position == 0) { "Position failed: ${dial.position} != 0" }
        assert(dial.zeroStops == 1) { "Zero stops failed: ${dial.zeroStops} != 1" }
    }

    @Test
    fun `D2 - Large positive rotation`() {
        val dial = Day202501.Dial2
        dial.right(200)
        assert(dial.position == 50) { "Position failed: ${dial.position} != 50" }
        assert(dial.zeroStops == 2) { "Zero stops failed: ${dial.zeroStops} != 2" }
    }

    @Test
    fun `D2 - Large negative rotation`() {
        val dial = Day202501.Dial2
        dial.left(200)
        assert(dial.position == 50) { "Position failed: ${dial.position} != 50" }
        assert(dial.zeroStops == 2) { "Zero stops failed: ${dial.zeroStops} != 2" }
    }

    @Test
    fun `D2 - Multiple negative rotation`() {
        val dial = Day202501.Dial2
        dial.left(100)
        dial.left(100)
        assert(dial.position == 50) { "Position failed: ${dial.position} != 50" }
        assert(dial.zeroStops == 2) { "Zero stops failed: ${dial.zeroStops} != 2" }
    }

    @Test
    fun `D2 - zero to zero - left`() {
        val dial = Day202501.Dial2
        dial.left(50)
        dial.left(100)
        assert(dial.position == 0) { "Position failed: ${dial.position} != 0" }
        assert(dial.zeroStops == 2) { "Zero stops failed: ${dial.zeroStops} != 2" }
    }

    @Test
    fun `D2 - zero to zero - right`() {
        val dial = Day202501.Dial2
        dial.right(50)
        dial.right(100)
        assert(dial.position == 0) { "Position failed: ${dial.position} != 0" }
        assert(dial.zeroStops == 2) { "Zero stops failed: ${dial.zeroStops} != 2" }
    }

    @Test
    fun `D2 - full positive rotation`() {
        val dial = Day202501.Dial2
        dial.right(100)
        assert(dial.position == 50) { "Position failed: ${dial.position} != 50" }
        assert(dial.zeroStops == 1) { "Zero stops failed: ${dial.zeroStops} != 1" }
    }

    @Test
    fun `D2 - full negative rotation`() {
        val dial = Day202501.Dial2
        dial.left(100)
        assert(dial.position == 50) { "Position failed: ${dial.position} != 50" }
        assert(dial.zeroStops == 1) { "Zero stops failed: ${dial.zeroStops} != 1" }
    }

    @Test
    fun `D2 - Forward and backwards`() {
        val dial = Day202501.Dial2
        dial.right(100)
        dial.left(100)

        assert(dial.position == 50) { "Position failed: ${dial.position} != 50" }
        assert(dial.zeroStops == 2) { "Zero stops failed: ${dial.zeroStops} != 2" }
    }

    @Test
    fun `D2 - Large rotations`() {
        val dial = Day202501.Dial2
        dial.right(500)
        assert(dial.position == 50) { "Position failed: ${dial.position} != 50" }
        assert(dial.zeroStops == 5) { "Zero stops failed: ${dial.zeroStops} != 5" }
        dial.left(500)
        assert(dial.position == 50) { "Position failed: ${dial.position} != 50" }
        assert(dial.zeroStops == 10) { "Zero stops failed: ${dial.zeroStops} != 10" }
    }


//    @Test
//    fun `correct negative rotation`() {
//        val dial = Day202501.Dial
//        dial.right(10) // 50 + 10 = 60
//        dial.left(20) // 60 - 20 = 40
//        assert(dial.position == 40) { "Position failed: ${dial.position} != 40"}
//
//    }
//
//    @Test
//    fun `correct zero detection`() {
//        val dial = Day202501.Dial
//        dial.right(5)
//        dial.left(5)
//        dial.right(5)
//        assert(dial.position == 5)
//        assert(dial.zeroStops == 1)
//    }
//
//    @Test
//    fun `correct full spin`() {
//        val dial = Day202501.Dial
//        dial.right(5)
//        dial.left(100)
//        assert(dial.position == 5)
//        assert(dial.zeroStops == 0)
//    }
//
//    @Test
//    fun `correct positive rotation`() {
//        val dial = Day202501.Dial
//        dial.right(95)
//        dial.right(5)
//        assert(dial.position == 0)
//        assert(dial.zeroStops == 1)
//    }
//
//    @Test
//    fun `correct complex`() {
//        val dial = Day202501.Dial
//        dial.left(5)
//        dial.right(5)
//        dial.right(5)
//        dial.left(5)
//        assert(dial.position == 0) { "Position failed: ${dial.position} != 0" }
//        assert(dial.zeroStops == 2) { "Zero stops failed: ${dial.zeroStops} != 2"}
//    }
//
//    @Test
//    fun `several rotations`() {
//        val dial = Day202501.Dial
//        dial.right(100)
//        dial.right(100)
//        dial.right(200)
//        assert(dial.position == 0) { "Position failed: ${dial.position} != 0" }
//        assert(dial.zeroStops == 3) { "Zero stops failed: ${dial.zeroStops} != 3"}
//    }
//
    @Test
    fun `D1 - sample test`() {
        val dial = Day202501.Dial
        val checkpoints = listOf(Pair(-68, 82), Pair(-30, 52), Pair(48, 0), Pair(-5, 95), Pair(60, 55), Pair(-55, 0), Pair(-1, 99), Pair(-99, 0), Pair(14, 14), Pair(-82, 32))
        checkpoints.forEach { (adj, check) ->
            println("Adjusting $adj with checkpoint $check")
            if(adj > 0) dial.right(adj) else dial.left(abs(adj))
            assert(dial.position == check)
        }
        assert(dial.zeroStops == 3)
    }

    @Test
    fun `D2 - sample test`() {
        val dial = Day202501.Dial2
        val checkpoints = listOf(Pair(-68, 82), Pair(-30, 52), Pair(48, 0), Pair(-5, 95), Pair(60, 55), Pair(-55, 0), Pair(-1, 99), Pair(-99, 0), Pair(14, 14), Pair(-82, 32))
        println("The dial starts by pointing at ${dial.position}")
        checkpoints.forEach { (adj, check) ->
//            println("Adjusting $adj with checkpoint $check")
            print("The dial is rotated $adj to point at ")
            if(adj > 0) dial.right(adj) else dial.left(abs(adj))
            assert(dial.position == check) { "Position check failed: ${dial.position} != $check" }
            println()
        }
        assert(dial.zeroStops == 6) { "Zero stops check failed: ${dial.zeroStops} != 6"}
    }
//
//    @Test
//    fun `D2 - simple negative click over`() {
//        val dial = Day202501.Dial2
//        dial.left(5)
//        assert(dial.position == 95)
//        assert(dial.zeroStops == 1)
//    }
//
//    @Test
//    fun `D2 - simple positive click over`() {
//        val dial = Day202501.Dial2
//        dial.right(95)
//        dial.right(10)
//        assert(dial.position == 5) { "position failed: ${dial.position} != 5"}
//        assert(dial.zeroStops == 1) { "zero stops failed: ${dial.zeroStops} != 1"}
//    }
//
//    @Test
//    fun `D2 - multiple click overs`() {
//        val dial = Day202501.Dial2
//        dial.right(95)
//        dial.right(200)
//        assert(dial.position == 95) { "position failed: ${dial.position} != 95"}
//        assert(dial.zeroStops == 2) { "zero stops failed: ${dial.zeroStops} != 2"}
//    }
//
//    @Test
//    fun `D2 - click over exact`() {
//        val dial = Day202501.Dial2
//        dial.right(95)
//        dial.right(5)
//        assert(dial.position == 0) { "position failed: ${dial.position} != 0"}
//        assert(dial.zeroStops == 1) { "zero stops failed: ${dial.zeroStops} != 1"}
//    }
//
//    @Test
//    fun `D2 - multiple click over exact`() {
//        val dial = Day202501.Dial2
//        dial.right(5)
//        dial.left(10)
//        dial.right(5)
//        assert(dial.position == 0) { "position failed: ${dial.position} != 0"}
//        assert(dial.zeroStops == 2) { "zero stops failed: ${dial.zeroStops} != 2"}
//    }
//
//    @Test
//    fun `D2 - click over left`() {
//        val dial = Day202501.Dial2
//        dial.right(5)
//        dial.left(10)
//
//        assert(dial.position == 95) { "position failed: ${dial.position} != 95"}
//        assert(dial.zeroStops == 1) { "zero stops failed: ${dial.zeroStops} != 1"}
//    }
//    @Test
//    fun `D2 - click over right`() {
//        val dial = Day202501.Dial2
//        dial.right(95)
//        dial.right(10)
//        assert(dial.position == 5) { "position failed: ${dial.position} != 5"}
//        assert(dial.zeroStops == 1) { "zero stops failed: ${dial.zeroStops} != 1"}
//    }
//
//    @Test
//    fun `D2 - zero turn`() {
//        val dial = Day202501.Dial2
//        dial.left(0)
//        dial.right(0)
//        assert(dial.position == 0) { "position failed: ${dial.position} != 0"}
//        assert(dial.zeroStops == 0) { "zero stops failed: ${dial.zeroStops} != 0"}
//    }
//    @Test
//    fun `D2 - full left test`() {
//        val dial = Day202501.Dial2
//        dial.left(95)
//        dial.left(5)
//        dial.left(200)
//        assert(dial.position == 90) { "position failed: ${dial.position} != 0"}
//        assert(dial.zeroStops == 3) { "zero stops failed: ${dial.zeroStops} != 3"}
//    }
}
