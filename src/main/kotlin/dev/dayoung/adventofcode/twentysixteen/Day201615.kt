package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.twentysixteen.Day201615.Disc.Companion.toDiscs

class Day201615 : PuzzleSolution(2016, 15) {
    data class Disc(val positions: Int, val startPos : Int) {
        fun posAtTime(time: Int): Int {
            return (startPos + time) % positions
        }

        companion object {
            private val parser = Regex("""Disc #\d+ has (\d+) positions; at time=0, it is at position (\d+).""")
            fun List<String>.toDiscs(): List<Disc> = mapNotNull { line ->
                parser.find(line)?.let {
                    Disc(it.groups[1]!!.value.toInt(), it.groups[2]!!.value.toInt())
                }
            }
        }
    }

    fun positionMap(t: Int, discs: List<Disc>): List<Int> {
        val pos = discs.foldIndexed(listOf<Int>()) { idx, acc, disc ->
            acc + disc.posAtTime(t + idx + 1)
        }
        return  pos
    }
    fun positionSequence(seed: Int, discs: List<Disc>) = sequence {
        var idx = seed
        var positions = positionMap(idx, discs)
        while(true) {
            yield(Pair(idx, positions))
            positions = positionMap(++idx, discs)
        }
    }

    fun partOne(discs: List<Disc>): Int {
        val posSeq = positionSequence(0, discs).first { it.second.all { i -> i == 0 }}
        println("Matching : ${posSeq.first} -> ${posSeq.second}")
        return posSeq.first
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource(sampleMode, "2016/fifteen.txt")?.let {
            val discs = it.toDiscs()
            partOne(discs)
            partOne(discs + Disc(11, 0))
        }
    }
}

fun main() {
    Day201615().solve(false)
}
