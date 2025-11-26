package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component

@Component
class Day201613 : PuzzleSolution(2016, 13){

    fun partOne(input: String): Int {

        return 0
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource(sampleMode, "2016/thirteen.txt")?.let {
            partOne(it.first())
        }
    }
}

fun main() {
    Day201613().solve(false)
}
