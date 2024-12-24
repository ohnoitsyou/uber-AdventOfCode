package dev.dayoung.adventofcode.twentytwentyfour

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

fun main() {
    Day202409().solve(false)
}

@Component
class Day202409: PuzzleSolution(2024, 9, true) {
    private val log = KotlinLogging.logger {}

    sealed interface Block
    private class File(val idx: Long) : Block {
        override fun toString(): String  = "$idx"
    }
    private data object Free : Block {
        override fun toString(): String = "."
    }

    private fun makeDisk(input: String): List<Block> {
        val i = if(input.length % 2 != 0) input + "0" else input
        return buildList {
            i.toList().chunked(2).forEachIndexed { idx, (f, fs) ->
                repeat(f.digitToInt())  { add(File(idx.toLong())) }
                repeat(fs.digitToInt()) { add(Free) }
            }
        }
    }

    private fun partOne(input: String): Long {
        val diskString = makeDisk(input)
        return compress(diskString.toMutableList(), 0, diskString.lastIndex, 0)
    }

    private tailrec fun compress(diskString: List<Block>, curIdx: Int, replaceIdx: Int, runningTotal: Long): Long {
        if(curIdx > replaceIdx) return runningTotal
        return when(diskString[curIdx]) {
            is File -> {
                val value = (diskString[curIdx] as File).idx * curIdx
                compress(diskString, curIdx + 1, replaceIdx, runningTotal + value)
            }
            is Free -> {
                val value = (diskString[replaceIdx] as File).idx * curIdx
                val ns = diskString.dropLast(1).dropLastWhile { it is Free }
                compress(ns, curIdx + 1, ns.lastIndex, runningTotal + value)
            }
        }
    }

    private val parser = { input: List<String> -> input.first().trim() }

    override fun solve(sampleMode: Boolean) {
        log.info { "2024 - Day 07" }
        val lines = Utils.readInputResource(sampleMode, "2024/nine.txt")?.let { parser(it) }

//        partOne(lines!!)
        log.info { "Part One: ${partOne(lines!!)}" }
        // Correct: 6344673854800
        //             5642189670
        // Not right:  1347222374
        // Not right: 15003605633
    }
}
